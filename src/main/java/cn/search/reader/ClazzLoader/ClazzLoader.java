package cn.search.reader.ClazzLoader;

import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Enum.SpecialClazzType;
import cn.search.reader.Usinged.U2;
import cn.search.runtime.Heap;
import cn.search.runtime.MethodArea;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static cn.search.reader.Enum.SpecialClazzType.Array;

/**
 * todo 1.增加从Path实时加载的类加载方式
 */
@Data
@Slf4j
public abstract class ClazzLoader {

    private final ClazzLoader parentClazzLoader;

    private HashMap<String, InputStream> lazyClazzMap = new HashMap<>();

    private HashMap<String, Integer> loadedClazzMap = new HashMap<>();

    // 当前类加载器的路径，当前类加载器负责路径内的类加载
    private String[] clazzPath;

    static {
        // 直接加载特殊类型的clazz对象，不通过类加载机制
        for (SpecialClazzType clazzType : SpecialClazzType.values()) {
            if (Objects.isNull(clazzType.getClazzName()))
                continue;
            Clazz clazz = new Clazz(clazzType.getClazzName());
            Integer index = Heap.putIntoObjectPool(clazz);
            MethodArea.CLAZZ_MAP.put(clazzType.getClazzName(), index);
        }
    }

    private static final ConstantClassInfo[] arrayClazzInterfaces = new ConstantClassInfo[]{
            new ConstantClassInfo().setName(new ConstantUtf8Info().setUtf8Info("java/lang/Cloneable")),
            new ConstantClassInfo().setName(new ConstantUtf8Info().setUtf8Info("java/io/Serializable"))
    };

    private static final ConstantClassInfo objectClazz = new ConstantClassInfo().setName(new ConstantUtf8Info().setUtf8Info("java/lang/Object"));

    private static final String[] arrayAccessFlag = Clazz.resolveAccessFlag(new U2(1041));

    public ClazzLoader(ClazzLoader parentClazzLoader, String[] clazzPath) {
        this.parentClazzLoader = parentClazzLoader;
        this.setClazzPath(clazzPath);
        try {
            this.getAllNeedLoadFile();
        } catch (IOException e) {
            log.error("ClazzLoader Constructor IOException = {} \n StackTrace = {}", e.getMessage(), e.getStackTrace());
        }
    }

    protected final Clazz defineClazz(String name) {
        InputStream classFileInputStream = this.lazyClazzMap.get(name);
        if (classFileInputStream != null && !MethodArea.CLAZZ_MAP.containsKey(name)) {
            // 加锁，防止重复加载
            synchronized (classFileInputStream) {
                if (!MethodArea.CLAZZ_MAP.containsKey(name)) {
                    Clazz entryClazz = null;
                    try {
                        DataInputStream dataInput = new DataInputStream(classFileInputStream);
                        entryClazz = new Clazz(dataInput);
                        entryClazz.setClazzLoader(this);
                        classFileInputStream.close();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Integer index = Heap.putIntoObjectPool(entryClazz);
                    MethodArea.CLAZZ_MAP.put(name, index);
                    this.loadedClazzMap.put(name, index);
                    return entryClazz;
                }
            }
        }
        return (Clazz) Heap.getObjectFromPool(MethodArea.CLAZZ_MAP.get(name));
    }

    // 实现双亲委派机制
    public Clazz loadClazz(String name) {
        // 判断当前类加载器是否加载过
        Integer currentIndex = this.loadedClazzMap.get(name);
        if (currentIndex != null) {
            return (Clazz) Heap.getObjectFromPool(currentIndex);
        } else {
            if (this.parentClazzLoader != null) {
                // 委托父类加载器加载，如果返回null则自行加载
                Clazz parentLoadedClazz = this.parentClazzLoader.loadClazz(name);
                if (parentLoadedClazz != null)
                    return parentLoadedClazz;
            }
            // 自行加载
            return this.findClazz(name);
        }
    }

    // 具体加载
    // 判断当前类加载器的clazzPath中是否存在全限定名为name的类
    protected Clazz findClazz(String name) {
        if (this.lazyClazzMap.containsKey(name)) {
            return this.defineClazz(name);
        } else {
            // 尝试在clazzPath中查找全限定名
            return null;
        }
    }

    // 根据全限定名加载数组类的clazz对象，不通过类加载机制
    public static Clazz loadArrayClazz(String name) {
        // 判断是否为数组
        if (name.startsWith(Array.getDescriptor())) {
            Clazz arrayClazz = (Clazz) Heap.getObjectFromPool(MethodArea.CLAZZ_MAP.get(name));
            if (Objects.isNull(arrayClazz)) {
                arrayClazz = new Clazz(name);
                arrayClazz.setInterfacesInfo(arrayClazzInterfaces);
                arrayClazz.setSuperClassInfo(objectClazz);
                arrayClazz.setClazzLoader(AppClazzLoader.getInstance());
                arrayClazz.setAccessFlagsName(arrayAccessFlag);
                Integer index = Heap.putIntoObjectPool(arrayClazz);
                MethodArea.CLAZZ_MAP.put(name, index);
            }
            return arrayClazz;
        } else {
            log.error("ClazzLoader loadArrayClazz not arrayClazz name = {}", name);
            return null;
        }
    }

    // 从clazzPath中懒加载所有的类，包含jar包和class文件
    protected final void getAllNeedLoadFile() throws IOException {
        for (String path : this.clazzPath) {
            File fileInput = new File(path);
            if (fileInput.isFile()) {
                getAllNeedLoadFile(fileInput, "");
            } else if (fileInput.isDirectory()) {
                File[] fileList = fileInput.listFiles();
                assert fileList != null;
                for (File file : fileList) {
                    getAllNeedLoadFile(file, "");
                }
            }
        }
    }

    protected final void getAllNeedLoadFile(File file, String path) throws IOException {
        if (file.getName().endsWith(".jar")) {
            // 如果是jar包则解析内部字节码文件
            try {
                JarFile jarFile = new JarFile(file);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    if (entryName.endsWith(".class")) {
                        this.lazyClazzMap.put(entryName.substring(0, entryName.length() - 6), jarFile.getInputStream(entry));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.getName().endsWith(".class")) {
            // 如果是字节码文件需要加上全限定名
            this.lazyClazzMap.put(path + file.getName().substring(0, file.getName().length() - 6), Files.newInputStream(file.toPath()));
        } else if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            assert fileList != null;
            for (File f : fileList) {
                // 递归，传入path
                getAllNeedLoadFile(f, path + file.getName() + File.separator);
            }
        }
    }

    // 销毁类加载器的时候释放所有的文件流
    @Override
    protected void finalize() throws Throwable {
        try {
            this.lazyClazzMap.forEach((n, f) -> {
                try {
                    f.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            super.finalize();
        }
    }

    protected String[] getClazzPath() {
        return clazzPath;
    }

    protected void setClazzPath(String[] clazzPath) {
        this.clazzPath = clazzPath;
    }

}

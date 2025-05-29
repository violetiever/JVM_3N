package cn.search.reader.ClazzLoader;

import cn.search.reader.ClassFileReader;
import cn.search.reader.Clazz.Clazz;
import cn.search.runtime.Heap;
import cn.search.runtime.MethodArea;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * todo 1.增加双亲委派机制。2.生成的clazz类放入到堆中
 */
@Data
public abstract class ClazzLoader {

    private final ClazzLoader parentClazzLoader;

    public HashMap<String, InputStream> lazyClazzMap = new HashMap<>();

    public ClazzLoader(ClazzLoader parentClazzLoader) {
        this.parentClazzLoader = parentClazzLoader;
    }

    public Integer loadClazzFromLazyClazzMap(String name) {

        InputStream classFileInputStream = this.lazyClazzMap.get(name);
        if (classFileInputStream != null && !MethodArea.CLAZZ_MAP.containsKey(name)) {
            MethodArea.CLAZZ_MAP.put(name, null);
            Clazz entryClazz = ClassFileReader.ClassFileToClazz(classFileInputStream);
            entryClazz.setClazzLoader(this);
            try {
                classFileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Integer index = Heap.putIntoObjectPool(entryClazz);

            MethodArea.CLAZZ_MAP.remove(name);
            MethodArea.CLAZZ_MAP.put(name, index);
            return index;
        } else {
            return MethodArea.CLAZZ_MAP.get(name);
        }

    }

    /**
     * 懒加载jar包
     * @param jarFile
     */
    public void lazyLoadJarClass(JarFile jarFile){
        try {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    this.lazyClazzMap.put(entryName.substring(0, entryName.length() - 6), jarFile.getInputStream(entry));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

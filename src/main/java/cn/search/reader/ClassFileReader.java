package cn.search.reader;


import cn.search.reader.Clazz.Clazz;
import cn.search.reader.ClazzLoader.BootStrapClazzLoader;
import cn.search.reader.ClazzLoader.ExtClazzLoader;
import cn.search.runtime.MethodArea;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class ClassFileReader {

    public static void main(String[] args) {
        String javaHome = System.getProperty("java.home");
        System.out.println(javaHome);

        // 将lib文件夹下的jar包懒加载到方法区
        List<JarFile> libFileList = new ArrayList<>();
        getAllJarFile(new File(javaHome + "\\lib"), libFileList);


        BootStrapClazzLoader bootStrapClazzLoader = BootStrapClazzLoader.getInstance();
        libFileList.forEach(bootStrapClazzLoader::lazyLoadJarClass);

        List<JarFile> extFileList = new ArrayList<>();
        getAllJarFile(new File(javaHome + "\\lib\\ext"), extFileList);

        ExtClazzLoader extClazzLoader = ExtClazzLoader.getInstance();
        extFileList.forEach(extClazzLoader::lazyLoadJarClass);

        Clazz clazz = ClassFileReader.ClassFileToClazz("G:/project/JVM_3N/ClassFileReader.class");


        // 关闭所有文件流
        libFileList.forEach(f-> {
            try {
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println(clazz.toString());

    }


    /**
     * 读取字节码文件，转换成16进制存储
     *
     * @param absolutePath 绝对路径
     * @return
     */
    public byte[] ClassToByteArray(String absolutePath) {
        byte[] result;

        try {
            File file = new File(absolutePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            long length = file.length();
            result = new byte[Long.valueOf(length).intValue()];
            fileInputStream.read(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("ClassToHex FileNotFoundException absolutePath = {}", absolutePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ClassToHex IOException absolutePath = {}", absolutePath);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Clazz ClassFileToClazz(InputStream inputStream) {
        Clazz clazz = null;
        try {
            DataInputStream dataInput = new DataInputStream(inputStream);
            clazz = new Clazz(dataInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("ClassFileToClazz FileNotFoundException absolutePath = {}", inputStream);
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ClassFileToClazz IOException absolutePath = {}", inputStream);
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }
        return clazz;
    }

    private static Clazz ClassFileToClazz(String absolutePath) {
        Clazz clazz = null;
        try {
            DataInputStream dataInput = new DataInputStream(new FileInputStream(absolutePath));
            clazz = new Clazz(dataInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("ClassFileToClazz FileNotFoundException absolutePath = {}", absolutePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ClassFileToClazz IOException absolutePath = {}", absolutePath);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }


    public static void getAllJarFile(File fileInput, List<JarFile> allFileList) {
        // 获取文件列表
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            if(file.getName().endsWith(".jar")){
                // 如果是jar包则将其加入到list中
                try {
                    allFileList.add(new JarFile(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

}

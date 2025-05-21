package cn.search.reader;


import cn.search.reader.Clazz.Clazz;
import cn.search.runtime.MethodArea;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class ClassFileReader {

    public static void main(String[] args) {
        String javaHome = System.getProperty("java.home");
        System.out.println(javaHome);

        ClassFileReader classFileReader = new ClassFileReader();
        String rtPath = javaHome + "\\lib\\rt.jar";

        JarFile jarFile;
        try {
            jarFile = new JarFile(rtPath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    InputStream inputStream = jarFile.getInputStream(entry);
                    Clazz entryClazz = classFileReader.ClassFileToClazz(inputStream);
                    MethodArea.CLAZZ_MAP.put(entryName.substring(0, entryName.length() - 6), entryClazz);
                    inputStream.close();
                }
                System.out.println(entryName);
            }
            jarFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Clazz clazz = classFileReader.ClassFileToClazz("G:/project/JVM_3N/ClassFileReader.class");


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

    private Clazz ClassFileToClazz(InputStream inputStream) {
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

    private Clazz ClassFileToClazz(String absolutePath) {
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

}

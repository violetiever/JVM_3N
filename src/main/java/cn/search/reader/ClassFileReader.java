package cn.search.reader;



import cn.search.reader.Clazz.Clazz;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ClassFileReader {

    public static void main(String[] args) {
        ClassFileReader classFileReader = new ClassFileReader();
        Clazz clazz = classFileReader.ClassFileToClazz("G:/project/JVM_3N/ClassFileReader.class");

        System.out.println(clazz.toString());

    }





    /**
     *  读取字节码文件，转换成16进制存储
     * @param absolutePath 绝对路径
     * @return
     */
    public byte[] ClassToByteArray(String absolutePath){
        byte[] result;

        try {
            File file = new File(absolutePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            long length = file.length();
            result = new byte[Long.valueOf(length).intValue()];
            fileInputStream.read(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("ClassToHex FileNotFoundException absolutePath = {}",absolutePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ClassToHex IOException absolutePath = {}",absolutePath);
            throw new RuntimeException(e);
        }
        return result;
    }

    private Clazz ClassFileToClazz(String absolutePath){
        Clazz clazz = null;
        try {
            DataInputStream dataInput = new DataInputStream(new FileInputStream(absolutePath));
            clazz = new Clazz(dataInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("ClassFileToClazz FileNotFoundException absolutePath = {}",absolutePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ClassFileToClazz IOException absolutePath = {}",absolutePath);
            throw new RuntimeException(e);
        }
        return clazz;
    }

}

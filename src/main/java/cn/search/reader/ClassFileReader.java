package cn.search.reader;


import cn.search.reader.Clazz.Clazz;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassFileReader {

    public static void main(String[] args) {
        String javaHome = System.getProperty("java.home");
        System.out.println(javaHome);

        Clazz string = AppClazzLoader.getInstance().loadClazz("java/lang/String");

        System.out.println(string.toString());

    }

}

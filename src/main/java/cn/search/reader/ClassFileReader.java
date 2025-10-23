package cn.search.reader;


import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import cn.search.runtime.Frame;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
public class ClassFileReader {

    public static void main(String[] args) {
        String javaHome = System.getProperty("java.home");
        System.out.println(javaHome);

        Clazz string = AppClazzLoader.getInstance().loadClazz("Scratch");
        string.resolve();
        MethodInfo method = string.getMethods()[0];
        Frame newFrame = new Frame(null,
                new Long[4],
                new Stack<>(),
                method.getThisClazz().getConstantPool(),
                method,
                method.getCodeAttribute().getCode(),
                method.getThisClazz());
        newFrame.execute();
        System.out.println("test");

    }

}

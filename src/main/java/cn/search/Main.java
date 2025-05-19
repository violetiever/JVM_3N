package cn.search;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends ClassLoader{
    public static void main(String[] args) throws Exception{

        System.out.println(System.getProperty("user.dir"));
        ClassLoader appClassLoader = Main.class.getClassLoader();

        Main loader = new Main();
        // 3. 加载并实例化类（注意全限定类名）
        Class<?> jvmTest = loader.findClass("JVMTest");
        //Class<?> jvmTest = appClassLoader.loadClass("JVMTest");
        for (Method method : jvmTest.getDeclaredMethods()) {
            System.out.println(method.getName());
        }
        Method test1 = jvmTest.getDeclaredMethod("main",String[].class);
        Object instance = jvmTest.getDeclaredConstructor().newInstance();

    }

    // 自定义加载逻辑
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // 1. 读取外部字节码文件（需完整路径）
            byte[] classBytes = Files.readAllBytes(Paths.get("G:/project/JVM_3N/JVMTest.class"));

            // 2. 将字节数组转为 Class 对象
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class: " + name, e);
        }
    }

    public static String test1(){
        return "Hello world!";
    }
}
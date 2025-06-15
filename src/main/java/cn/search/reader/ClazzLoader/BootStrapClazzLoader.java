package cn.search.reader.ClazzLoader;


import java.io.File;

public class BootStrapClazzLoader extends ClazzLoader {

    private static final BootStrapClazzLoader instance = new BootStrapClazzLoader();

    /**
     * 引导类加载器
     */
    public BootStrapClazzLoader() {
        super(null, new String[]{System.getProperty("java.home") + File.separator + "lib"});
    }

    public static BootStrapClazzLoader getInstance() {
        return BootStrapClazzLoader.instance;
    }

}

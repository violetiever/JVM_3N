package cn.search.reader.ClazzLoader;

import java.io.File;

public class ExtClazzLoader extends ClazzLoader {

    private static final ExtClazzLoader instance = new ExtClazzLoader();

    /**
     * 扩展类加载器
     */
    private ExtClazzLoader() {
        super(BootStrapClazzLoader.getInstance(), new String[]{System.getProperty("java.home") + File.separator + "lib" + File.separator + "ext"});
    }

    public static ExtClazzLoader getInstance() {
        return ExtClazzLoader.instance;
    }

}

package cn.search.reader.ClazzLoader;

import java.io.File;

public class AppClazzLoader extends ClazzLoader {

    private static final AppClazzLoader instance = new AppClazzLoader();

    private AppClazzLoader() {
        super(ExtClazzLoader.getInstance(), System.getProperty("java.class.path").split(File.pathSeparator));
    }

    public static AppClazzLoader getInstance() {
        return AppClazzLoader.instance;
    }

}

package cn.search.reader.ClazzLoader;

import java.util.jar.JarFile;

public class BootStrapClazzLoader extends ClazzLoader{

    static BootStrapClazzLoader instance = new BootStrapClazzLoader();

    public BootStrapClazzLoader(ClazzLoader parentClazzLoader) {
        super(parentClazzLoader);
    }

    /**
     * 引导类加载器
     */
    public BootStrapClazzLoader() {
        super(null);
    }

    public static BootStrapClazzLoader getInstance(){
        return BootStrapClazzLoader.instance;
    }

    @Override
    public Integer loadClazzFromLazyClazzMap(String name) {
        if(this.lazyClazzMap.containsKey(name)){
            return super.loadClazzFromLazyClazzMap(name);
        }else {
            return null;
        }
    }

    @Override
    public void lazyLoadJarClass(JarFile jarFile){
        super.lazyLoadJarClass(jarFile);
    }


}

package cn.search.reader.ClazzLoader;

import java.util.jar.JarFile;

public class ExtClazzLoader extends ClazzLoader{

    static ExtClazzLoader instance = new ExtClazzLoader();

    public ExtClazzLoader(ClazzLoader parentClazzLoader) {
        super(parentClazzLoader);
    }

    /**
     * 扩展类加载器
     */
    public ExtClazzLoader() {
        super(BootStrapClazzLoader.getInstance());
    }

    public static ExtClazzLoader getInstance(){
        return ExtClazzLoader.instance;
    }

    @Override
    public Integer loadClazzFromLazyClazzMap(String name) {
        Integer index = this.getParentClazzLoader().loadClazzFromLazyClazzMap(name);

        if(index != null){
            return index;
        }else {
            if (this.lazyClazzMap.containsKey(name)) {
                return super.loadClazzFromLazyClazzMap(name);
            } else {
                return null;
            }
        }
    }

    @Override
    public void lazyLoadJarClass(JarFile jarFile){
        super.lazyLoadJarClass(jarFile);
    }

}

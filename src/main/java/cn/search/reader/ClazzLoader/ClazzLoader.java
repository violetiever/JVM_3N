package cn.search.reader.ClazzLoader;

import cn.search.reader.ClassFileReader;
import cn.search.reader.Clazz.Clazz;
import cn.search.runtime.MethodArea;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;

/**
 * todo 1.增加双亲委派机制。2.生成的clazz类放入到堆中
 */
@Data
public abstract class ClazzLoader {

    private final ClazzLoader parentClazzLoader;

    public Clazz loadClazz(String name) {
        if(parentClazzLoader != null){
            return parentClazzLoader.loadClazz(name);
        }else {
            InputStream classFileInputStream = MethodArea.LAZY_CLAZZ_MAP.get(name);
            if (classFileInputStream != null && !MethodArea.CLAZZ_MAP.containsKey(name)) {
                MethodArea.CLAZZ_MAP.put(name, null);
                Clazz entryClazz = ClassFileReader.ClassFileToClazz(classFileInputStream);
                entryClazz.setClazzLoader(this);
                try {
                    classFileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MethodArea.CLAZZ_MAP.remove(name);
                MethodArea.CLAZZ_MAP.put(name, entryClazz);
                return entryClazz;
            }else {
                return MethodArea.CLAZZ_MAP.get(name);
            }
        }
    }

}

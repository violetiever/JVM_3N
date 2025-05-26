package cn.search.runtime;

import cn.search.reader.Clazz.Clazz;

import java.io.InputStream;
import java.util.HashMap;

public class MethodArea {

    /**
     * 存储类信息的Map，使用全限定名作为Key
     */
    public static HashMap<String, Clazz> CLAZZ_MAP = new HashMap<>();

    /**
     * 懒加载，先存储路径，需要时再加载
     */
    public static HashMap<String, InputStream> LAZY_CLAZZ_MAP = new HashMap<>();


}

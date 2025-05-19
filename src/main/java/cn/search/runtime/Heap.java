package cn.search.runtime;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;

@Slf4j
public class Heap {

    /**
     * 字符串常量池 todo 字符串常量池 新存入时需要在堆内新建对象，存入的是对象在堆中的索引
     */


    /**
     * 反向字符串常量池，存储字符串对应的索引
     */
    public static HashMap<char[], Integer> CONSTANT_STRING_POOL = new HashMap<>();


    /**
     * 对象池
     */
    public static HashMap<Integer, Object> OBJECT_POOL = new HashMap<>();

    public static HashMap<Object, Integer> OBJECT_POOL_REVERSE = new HashMap<>();

    public static Integer putIntoConstantStringPool(String s) {
        Integer index = putIntoObjectPool(s);
        try {
            // 反射获取value属性
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true);
            Heap.CONSTANT_STRING_POOL.putIfAbsent((char[]) valueField.get(s), index);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            log.error("Heap Error;Method = putIntoConstantStringPool;NoSuchFieldException;s ={} ", s);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error("Heap Error;Method = putIntoConstantStringPool;IllegalAccessException;s ={} ", s);
        }
        return index;
    }

    /**
     * 将o放入到堆的对象池中，返回索引
     *
     * @param o
     * @return
     */
    public static Integer putIntoObjectPool(Object o) {
        if (Heap.OBJECT_POOL_REVERSE.get(o) == null) {
            int poolIndex = Heap.OBJECT_POOL.size();
            Heap.OBJECT_POOL.put(poolIndex, o);
            Heap.OBJECT_POOL_REVERSE.put(o, poolIndex);
            return poolIndex;
        } else {
            return OBJECT_POOL_REVERSE.get(o);
        }
    }


}

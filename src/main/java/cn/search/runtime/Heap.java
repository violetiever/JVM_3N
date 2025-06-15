package cn.search.runtime;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class Heap {

    /**
     * 反向字符串常量池，存储字符串对应的索引 todo 修改为码点当作key
     */
    public static final HashMap<String, Integer> CONSTANT_STRING_POOL = new HashMap<>();

    /**
     * 对象池
     */
    public static final HashMap<Integer, Object> OBJECT_POOL = new HashMap<>();

    public static final HashMap<Object, Integer> OBJECT_POOL_REVERSE = new HashMap<>();

    public static Integer putIntoConstantStringPool(String s) {
        Integer index = putIntoObjectPool(s);
        Heap.CONSTANT_STRING_POOL.putIfAbsent(s, index);
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
            int poolIndex;
            synchronized (Heap.OBJECT_POOL) {
                if (Heap.OBJECT_POOL_REVERSE.get(o) == null) {
                    poolIndex = Heap.OBJECT_POOL.size();
                    Heap.OBJECT_POOL.put(poolIndex, o);
                    Heap.OBJECT_POOL_REVERSE.put(o, poolIndex);
                }else {
                    return Heap.OBJECT_POOL_REVERSE.get(o);
                }
            }
            return poolIndex;
        } else {
            return Heap.OBJECT_POOL_REVERSE.get(o);
        }
    }

    public static Object getObjectFromPool(Integer index) {
        return Heap.OBJECT_POOL.get(index);
    }

}

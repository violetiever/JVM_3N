package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.HashMap;
import java.util.function.Function;

/**
 * Java虚拟机规范.Java SE 8版 236页
 */
public class arraylength implements Opcode {

    static final HashMap<Class, Function<Object, Integer>> LENGTH_MAP = new HashMap<>();

    static {
        LENGTH_MAP.put(boolean[].class, arr -> ((boolean[]) arr).length);
        LENGTH_MAP.put(char[].class, arr -> ((char[]) arr).length);
        LENGTH_MAP.put(float[].class, arr -> ((float[]) arr).length);
        LENGTH_MAP.put(double[].class, arr -> ((double[]) arr).length);
        LENGTH_MAP.put(byte[].class, arr -> ((byte[]) arr).length);
        LENGTH_MAP.put(short[].class, arr -> ((short[]) arr).length);
        LENGTH_MAP.put(int[].class, arr -> ((int[]) arr).length);
        LENGTH_MAP.put(long[].class, arr -> ((long[]) arr).length);
        LENGTH_MAP.put(Object[].class, arr -> ((Object[]) arr).length);
    }

    @Override
    public void opt(Frame frame) {
        Object array = frame.getOperandStack().pop();
        Function<Object, Integer> function = LENGTH_MAP.get(array.getClass());
        Integer length = function.apply(array);
        frame.getOperandStack().push(length);
        frame.getNextCode();
    }

}

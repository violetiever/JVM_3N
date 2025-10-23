package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.HashMap;
import java.util.function.Function;

/**
 * Java虚拟机规范.Java SE 8版 311页
 */
public class newarray implements Opcode {

    static final HashMap<Integer, Function<Integer, Object>> T_MAP = new HashMap<>();

    static {
        T_MAP.put(4, boolean[]::new);
        T_MAP.put(5, char[]::new);
        T_MAP.put(6, float[]::new);
        T_MAP.put(7, double[]::new);
        T_MAP.put(8, byte[]::new);
        T_MAP.put(9, short[]::new);
        T_MAP.put(10, int[]::new);
        T_MAP.put(11, long[]::new);
    }


    @Override
    public void opt(Frame frame) {
        int atype = frame.getNextCode();
        int count = (int) frame.getOperandStack().pop();
        Object array = T_MAP.get(atype).apply(count);
        frame.getOperandStack().push(array);
        frame.getNextCode();
    }

}

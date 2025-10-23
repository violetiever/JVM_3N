package cn.search.intepreter.opt.Math.L;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 307页
 */
public class lushr implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value2 = (int) frame.getOperandStack().pop();
        long value1 = (long) frame.getOperandStack().pop();
        int s = value2 & 0b111111;
        long result = value1 >> s;
        if (value1 < 0)
            result = result + (2L << ~s);
        frame.getOperandStack().push(result);
        frame.getNextCode();
    }

}

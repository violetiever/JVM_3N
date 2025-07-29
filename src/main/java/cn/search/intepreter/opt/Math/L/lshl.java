package cn.search.intepreter.opt.Math.L;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 305页
 */
public class lshl implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value2 = (int) frame.getOperandStack().pop();
        long value1 = (long) frame.getOperandStack().pop();
        int s = value2 & 0b111111;
        long result = value1 << s;
        frame.getOperandStack().push(result);
    }

}

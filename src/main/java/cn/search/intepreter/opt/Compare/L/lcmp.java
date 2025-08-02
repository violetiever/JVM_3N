package cn.search.intepreter.opt.Compare.L;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 297页
 */
public class lcmp implements Opcode {

    @Override
    public void opt(Frame frame) {
        long value2 = (long) frame.getOperandStack().pop();
        long value1 = (long) frame.getOperandStack().pop();
        int result = value1 > value2 ? 1 : (value1 == value2 ? 0 : -1);
        frame.getOperandStack().push(result);
    }

}

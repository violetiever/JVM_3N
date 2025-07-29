package cn.search.intepreter.opt.Math.L;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 303页
 */
public class lor implements Opcode {

    @Override
    public void opt(Frame frame) {
        long value2 = (long) frame.getOperandStack().pop();
        long value1 = (long) frame.getOperandStack().pop();
        long result = value1 & value2;
        frame.getOperandStack().push(result);
    }

}

package cn.search.intepreter.opt.Math.D;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 247页
 */
public class ddiv implements Opcode {

    @Override
    public void opt(Frame frame) {
        double value2 = (double) frame.getOperandStack().pop();
        double value1 = (double) frame.getOperandStack().pop();
        double result = value1 / value2;
        frame.getOperandStack().push(result);
        frame.getNextCode();
    }

}

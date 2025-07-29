package cn.search.intepreter.opt.Math.D;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 244页
 */
public class dadd implements Opcode {

    @Override
    public void opt(Frame frame) {
        double value2 = (double) frame.getOperandStack().pop();
        double value1 = (double) frame.getOperandStack().pop();
        double result = value1 + value2;
        frame.getOperandStack().push(result);
    }

}

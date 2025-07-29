package cn.search.intepreter.opt.Math.F;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 259页
 */
public class fdiv implements Opcode {

    @Override
    public void opt(Frame frame) {
        float value2 = (float) frame.getOperandStack().pop();
        float value1 = (float) frame.getOperandStack().pop();
        float result = value1 / value2;
        frame.getOperandStack().push(result);
    }

}

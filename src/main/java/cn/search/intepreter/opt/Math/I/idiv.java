package cn.search.intepreter.opt.Math.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 271页
 */
public class idiv implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value2 = (int) frame.getOperandStack().pop();
        int value1 = (int) frame.getOperandStack().pop();
        int result = value1 / value2;
        frame.getOperandStack().push(result);
        frame.getNextCode();
    }

}

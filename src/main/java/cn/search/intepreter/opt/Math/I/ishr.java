package cn.search.intepreter.opt.Math.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 291页
 */
public class ishr implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value2 = (int) frame.getOperandStack().pop();
        int value1 = (int) frame.getOperandStack().pop();
        int s = value2 & 0b11111;
        int result = value1 >> s;
        frame.getOperandStack().push(result);
        frame.getNextCode();
    }

}

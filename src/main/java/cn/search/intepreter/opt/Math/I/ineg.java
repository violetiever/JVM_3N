package cn.search.intepreter.opt.Math.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 276页
 */
public class ineg implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value = (int) frame.getOperandStack().pop();
        frame.getOperandStack().push(-value);
        frame.getNextCode();
    }

}

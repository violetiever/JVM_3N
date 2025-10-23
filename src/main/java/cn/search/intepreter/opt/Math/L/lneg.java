package cn.search.intepreter.opt.Math.L;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 302页
 */
public class lneg implements Opcode {

    @Override
    public void opt(Frame frame) {
        long value = (long) frame.getOperandStack().pop();
        frame.getOperandStack().push(-value);
        frame.getNextCode();
    }

}

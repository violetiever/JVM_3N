package cn.search.intepreter.opt.Math.D;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 249页
 */
public class dneg implements Opcode {

    @Override
    public void opt(Frame frame) {
        double value = (double) frame.getOperandStack().pop();
        frame.getOperandStack().push(-value);
    }

}

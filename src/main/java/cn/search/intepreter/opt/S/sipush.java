package cn.search.intepreter.opt.S;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 316页
 */
public class sipush implements Opcode {

    @Override
    public void opt(Frame frame) {
        int instantNumHigh = frame.getNextCode();
        int instantNumLow = frame.getNextCode();
        int instantNum = (short) instantNumHigh << 8 | instantNumLow;
        frame.getOperandStack().push(instantNum);
        frame.getNextCode();
    }

}

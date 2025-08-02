package cn.search.intepreter.opt.D.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 248页
 */
public class dload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode().getValue();
        dloadBasic(frame, index);
    }

    public static void dloadBasic(Frame frame, int index) {
        double value = frame.getLocalVariable()[index].doubleValue();
        frame.getOperandStack().push(value);
    }

}

package cn.search.intepreter.opt.D.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 248页
 */
public class dload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        dloadBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void dloadBasic(Frame frame, int index) {
        double value = frame.getLocalVariable()[index].doubleValue();
        frame.getOperandStack().push(value);
    }

}

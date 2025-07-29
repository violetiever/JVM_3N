package cn.search.intepreter.opt.A.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 234页
 */
public class aload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        aloadBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void aloadBasic(Frame frame, int index) {
        int value = frame.getLocalVariable()[index].intValue();
        frame.getOperandStack().push(value);
    }

}

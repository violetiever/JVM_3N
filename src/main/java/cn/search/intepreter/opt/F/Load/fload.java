package cn.search.intepreter.opt.F.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 260页
 */
public class fload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        floadBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void floadBasic(Frame frame, int index) {
        float value = frame.getLocalVariable()[index].floatValue();
        frame.getOperandStack().push(value);
    }

}

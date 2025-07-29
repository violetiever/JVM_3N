package cn.search.intepreter.opt.F.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 264页
 */
public class fstore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        fstoreBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void fstoreBasic(Frame frame, int index) {
        float value = (float) frame.getOperandStack().pop();
        frame.getLocalVariable()[index] = (long) value;
    }

}

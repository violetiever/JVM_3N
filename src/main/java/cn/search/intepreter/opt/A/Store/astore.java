package cn.search.intepreter.opt.A.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 236页
 */
public class astore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        astoreBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void astoreBasic(Frame frame, int index) {
        int value = (int) frame.getOperandStack().pop();
        frame.getLocalVariable()[index] = (long) value;
    }

}

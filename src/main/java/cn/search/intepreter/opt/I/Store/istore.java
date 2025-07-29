package cn.search.intepreter.opt.I.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 291页
 */
public class istore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        istoreBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void istoreBasic(Frame frame, int index) {
        int value = (int) frame.getOperandStack().pop();
        frame.getLocalVariable()[index] = (long) value;
    }

}

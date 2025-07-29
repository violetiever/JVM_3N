package cn.search.intepreter.opt.D.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 251页
 */
public class dstore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        dstoreBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void dstoreBasic(Frame frame, int index) {
        double value = (double) frame.getOperandStack().pop();
        // double转换成long的形式存储
        frame.getLocalVariable()[index] = Double.doubleToLongBits(value);
        frame.getLocalVariable()[index + 1] = null;
    }

}

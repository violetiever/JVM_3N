package cn.search.intepreter.opt.L.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 305页
 */
public class lstore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        lstoreBasic(frame, index);
        frame.getNextCode();
    }

    public static void lstoreBasic(Frame frame, int index) {
        long value = (long) frame.getOperandStack().pop();
        frame.getLocalVariable()[index] = value;
        frame.getLocalVariable()[index + 1] = null;
    }

}

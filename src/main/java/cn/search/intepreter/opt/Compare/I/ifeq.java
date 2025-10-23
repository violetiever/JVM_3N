package cn.search.intepreter.opt.Compare.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.function.Predicate;

/**
 * Java虚拟机规范.Java SE 8版 273页
 */
public class ifeq implements Opcode {

    @Override
    public void opt(Frame frame) {
        Predicate<Integer> predicate = value -> value == 0;
        ifBasic(frame, predicate);
    }

    public static void ifBasic(Frame frame, Predicate<Integer> predicate) {
        int pc = frame.getPc();
        int value = (int) frame.getOperandStack().pop();
        int branchbyte1 = frame.getNextCode();
        int branchbyte2 = frame.getNextCode();
        if (predicate.test(value))
            frame.setPc(pc + ((branchbyte1 << 8) | branchbyte2));
    }

}

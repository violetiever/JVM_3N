package cn.search.intepreter.opt.Compare.IF;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.function.BiPredicate;

/**
 * Java虚拟机规范.Java SE 8版 272页
 */
public class if_acmpeq implements Opcode {

    @Override
    public void opt(Frame frame) {
        BiPredicate<Integer, Integer> biPredicate = (value1, value2) -> value1.equals(value2);
        ifAcmpBasic(frame, biPredicate);
    }

    public static void ifAcmpBasic(Frame frame, BiPredicate<Integer, Integer> biPredicate) {
        int pc = frame.getPc();
        int value2 = (int) frame.getOperandStack().pop();
        int value1 = (int) frame.getOperandStack().pop();
        int branchbyte1 = frame.getNextCode();
        int branchbyte2 = frame.getNextCode();
        if (biPredicate.test(value1, value2))
            frame.setPc(pc + ((branchbyte1 << 8) | branchbyte2));

    }

}

package cn.search.intepreter.opt.Compare.IF;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.function.BiPredicate;

/**
 * Java虚拟机规范.Java SE 8版 272页
 */
public class if_icmpeq implements Opcode {

    @Override
    public void opt(Frame frame) {
        BiPredicate<Integer, Integer> biPredicate = (value1, value2) -> value1.equals(value2);
        ifIcmpBasic(frame, biPredicate);
    }

    public static void ifIcmpBasic(Frame frame, BiPredicate<Integer, Integer> biPredicate) {
        int oriPc = frame.getPc();
        int value2 = (int) frame.getOperandStack().pop();
        int value1 = (int) frame.getOperandStack().pop();
        byte branchByte1 = frame.getNextByteCode();
        byte branchByte2 = frame.getNextByteCode();
        if (biPredicate.test(value1, value2))
            frame.setPc(oriPc + ((branchByte1 << 8) | branchByte2));
        else
            frame.getNextCode();
    }

}

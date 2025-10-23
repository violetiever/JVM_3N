package cn.search.intepreter.opt.D.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 248页
 */
public class dload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        dloadBasic(frame, index);
        frame.getNextCode();
    }

    // 本地变量表存储的double转换成longBits存储
    public static void dloadBasic(Frame frame, int index) {
        double value = Double.longBitsToDouble(frame.getLocalVariable()[index]);
        frame.getOperandStack().push(value);
    }

}

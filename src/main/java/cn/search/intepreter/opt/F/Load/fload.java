package cn.search.intepreter.opt.F.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 260页
 */
public class fload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        floadBasic(frame, index);
        frame.getNextCode();
    }

    // 本地变量表存储float转换成intBits存储
    public static void floadBasic(Frame frame, int index) {
        float value = Float.intBitsToFloat(frame.getLocalVariable()[index].intValue());
        frame.getOperandStack().push(value);
    }

}

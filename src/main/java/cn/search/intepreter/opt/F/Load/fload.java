package cn.search.intepreter.opt.F.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 260页
 */
public class fload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode().getValue();
        floadBasic(frame, index);
    }

    public static void floadBasic(Frame frame, int index) {
        float value = frame.getLocalVariable()[index].floatValue();
        frame.getOperandStack().push(value);
    }

}

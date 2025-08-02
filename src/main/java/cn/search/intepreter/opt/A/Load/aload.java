package cn.search.intepreter.opt.A.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 234页
 */
public class aload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode().getValue();
        aloadBasic(frame, index);
    }

    public static void aloadBasic(Frame frame, int index) {
        int value = frame.getLocalVariable()[index].intValue();
        frame.getOperandStack().push(value);
    }

}

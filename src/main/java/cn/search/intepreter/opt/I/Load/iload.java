package cn.search.intepreter.opt.I.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 275页
 */
public class iload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        iloadBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void iloadBasic(Frame frame, int index) {
        int value = frame.getLocalVariable()[index].intValue();
        frame.getOperandStack().push(value);
    }

}

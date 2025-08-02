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
        int index = frame.getNextCode().getValue();
        iloadBasic(frame, index);
    }

    public static void iloadBasic(Frame frame, int index) {
        int value = frame.getLocalVariable()[index].intValue();
        frame.getOperandStack().push(value);
    }

}

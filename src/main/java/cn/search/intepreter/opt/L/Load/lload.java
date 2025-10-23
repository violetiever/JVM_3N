package cn.search.intepreter.opt.L.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 301页
 */
public class lload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        lloadBasic(frame, index);
        frame.getNextCode();
    }

    public static void lloadBasic(Frame frame, int index) {
        Long value = frame.getLocalVariable()[index];
        frame.getOperandStack().push(value);
    }


}

package cn.search.intepreter.opt.L.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 301页
 */
public class lload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = frame.getCode()[pc + 1].getValue();
        lloadBasic(frame, index);
        frame.setPc(pc + 1);
    }

    public static void lloadBasic(Frame frame, int index) {
        Long value = frame.getLocalVariable()[index];
        frame.getOperandStack().push(value);
    }


}

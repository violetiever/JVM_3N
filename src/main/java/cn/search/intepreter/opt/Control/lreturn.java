package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 304页
 */
public class lreturn implements Opcode {

    @Override
    public void opt(Frame frame) {
        long value = (long) frame.getOperandStack().pop();
        frame.setPc(frame.getPc() + 1);
        frame = frame.getPreFrame();
        frame.getOperandStack().push(value);
    }

}

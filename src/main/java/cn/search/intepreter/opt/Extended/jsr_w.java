package cn.search.intepreter.opt.Extended;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 294页
 */
public class jsr_w implements Opcode {

    @Override
    public void opt(Frame frame) {
        int branchByte = (frame.getNextCode() << 24) | (frame.getNextCode() << 16) | (frame.getNextCode() << 8) | frame.getNextCode();
        int address = frame.getNextCode();
        frame.getOperandStack().push(address);
        frame.setPc(frame.getPc() + branchByte);
    }

}

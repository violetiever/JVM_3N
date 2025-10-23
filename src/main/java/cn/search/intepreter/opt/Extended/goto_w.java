package cn.search.intepreter.opt.Extended;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 267页
 */
public class goto_w implements Opcode {

    @Override
    public void opt(Frame frame) {
        int branchByte = (frame.getNextCode() << 24) | (frame.getNextCode() << 16) | (frame.getNextCode() << 8) | frame.getNextCode();
        frame.setPc(branchByte);
    }

}

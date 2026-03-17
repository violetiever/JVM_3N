package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 293页
 */
public class jsr implements Opcode {

    @Override
    public void opt(Frame frame) {
        int address = frame.getPc() + 1;
        frame.setPc(frame.getPc() + CommonUtil.parseBranchByte(frame.getNextCode(), frame.getNextCode()));
        frame.getOperandStack().push(address);
    }

}

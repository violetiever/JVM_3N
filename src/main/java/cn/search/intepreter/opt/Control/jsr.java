package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 293页
 */
public class jsr implements Opcode {

    @Override
    public void opt(Frame frame) {
        int address = frame.getPc() + 1;
        int branchbyte1 = frame.getNextCode();
        int branchbyte2 = frame.getNextCode();
        frame.setPc(frame.getPc() + ((branchbyte1 << 8) | branchbyte2));
        frame.getOperandStack().push(address);
    }

}

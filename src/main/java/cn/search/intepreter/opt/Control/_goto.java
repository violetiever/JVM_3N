package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 266页
 */
public class _goto implements Opcode {

    @Override
    public void opt(Frame frame) {
        int oriPc = frame.getPc();
        byte branchByte1 = frame.getNextByteCode();
        byte branchByte2 = frame.getNextByteCode();
        frame.setPc(oriPc + ((branchByte1 << 8) | branchByte2));
    }

}

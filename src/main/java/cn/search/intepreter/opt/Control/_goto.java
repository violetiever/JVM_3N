package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 266页
 */
public class _goto implements Opcode {

    @Override
    public void opt(Frame frame) {
        int branchbyte1 = frame.getNextCode().getValue();
        int branchbyte2 = frame.getNextCode().getValue();
        frame.setPc(frame.getPc() + ((branchbyte1 << 8) | branchbyte2));
    }

}

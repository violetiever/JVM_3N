package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 317页
 */
public class tableswitch implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (int) frame.getOperandStack().pop();
        int pc = frame.getPc();
        int pad = 4 - pc % 4;
        for (int i = 0; i < pad; i++)
            frame.getNextCode();
        int defaultbyte = getNext4CodeTo32BitSignedNum(frame);
        int lowbyte = getNext4CodeTo32BitSignedNum(frame);
        int highbyte = getNext4CodeTo32BitSignedNum(frame);
        if (index < lowbyte || index > highbyte) {
            frame.setPc(pc + defaultbyte);
            return;
        }
        for (int i = 0; i < index - lowbyte; i++) {
            getNext4CodeTo32BitSignedNum(frame);
        }
        frame.setPc(pc + getNext4CodeTo32BitSignedNum(frame));
    }

    protected static int getNext4CodeTo32BitSignedNum(Frame frame) {
        return (frame.getNextCode() << 24) |
                (frame.getNextCode() << 16) |
                (frame.getNextCode() << 8) |
                frame.getNextCode();
    }

}

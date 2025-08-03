package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 302页
 */
public class lookupswitch implements Opcode {

    @Override
    public void opt(Frame frame) {
        int key = (int) frame.getOperandStack().pop();
        int pc = frame.getPc();
        int pad = 4 - pc % 4;
        for (int i = 0; i < pad; i++)
            frame.getNextCode();
        int defaultbyte = tableswitch.getNext4CodeTo32BitSignedNum(frame);
        int npairs = tableswitch.getNext4CodeTo32BitSignedNum(frame);
        for (int i = 0; i < npairs; i++) {
            int match = tableswitch.getNext4CodeTo32BitSignedNum(frame);
            int offset = tableswitch.getNext4CodeTo32BitSignedNum(frame);
            if (key == match) {
                frame.setPc(pc + offset);
                return;
            }
        }
        frame.setPc(pc + defaultbyte);
    }

}

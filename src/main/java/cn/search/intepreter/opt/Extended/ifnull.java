package cn.search.intepreter.opt.Extended;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.Objects;

/**
 * Java虚拟机规范.Java SE 8版 274页
 */
public class ifnull implements Opcode {

    @Override
    public void opt(Frame frame) {
        int branchByte = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        Object value = frame.getOperandStack().pop();
        if (Objects.isNull(value)) {
            frame.setPc(branchByte);
        } else
            frame.getNextCode();
    }

}

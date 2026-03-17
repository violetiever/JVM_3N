package cn.search.intepreter.opt.Extended;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;

import java.util.Objects;

/**
 * Java虚拟机规范.Java SE 8版 274页
 */
public class ifnonnull implements Opcode {

    @Override
    public void opt(Frame frame) {
        int oriPc = frame.getPc();
        int branchByte = CommonUtil.parseBranchByte(frame.getNextCode(), frame.getNextCode());
        Object value = frame.getOperandStack().pop();
        if (Objects.nonNull(value)) {
            frame.setPc(oriPc + branchByte);
        } else
            frame.getNextCode();
    }

}

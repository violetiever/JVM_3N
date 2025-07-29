package cn.search.intepreter.opt.S;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 316页
 */
public class sipush implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        U1 instantNumHigh = frame.getCode()[pc + 1];
        U1 instantNumLow = frame.getCode()[pc + 2];
        int instantNum = (short) instantNumHigh.getValue() << 8 | instantNumLow.getValue();
        frame.getOperandStack().push(instantNum);
        frame.setPc(pc + 2);
    }

}

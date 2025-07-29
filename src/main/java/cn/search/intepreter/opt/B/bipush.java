package cn.search.intepreter.opt.B;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 240页
 */
public class bipush implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        U1 instantNum = frame.getCode()[pc + 1];
        frame.getOperandStack().push(instantNum.getValue());
        frame.setPc(pc + 1);
    }

}

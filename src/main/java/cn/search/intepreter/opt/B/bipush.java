package cn.search.intepreter.opt.B;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 240页
 */
public class bipush implements Opcode {

    @Override
    public void opt(Frame frame) {
        int instantNum = frame.getNextCode();
        frame.getOperandStack().push(instantNum);
        frame.getNextCode();
    }

}

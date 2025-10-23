package cn.search.intepreter.opt.Stack;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 312页
 */
public class pop implements Opcode {

    @Override
    public void opt(Frame frame) {
        frame.getOperandStack().pop();
        frame.getNextCode();
    }

}

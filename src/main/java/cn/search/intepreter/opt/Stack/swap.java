package cn.search.intepreter.opt.Stack;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 317页
 */
public class swap implements Opcode {

    @Override
    public void opt(Frame frame) {
        Object value1 = frame.getOperandStack().pop();
        Object value2 = frame.getOperandStack().pop();

        frame.getOperandStack().push(value1);
        frame.getOperandStack().push(value2);
    }

}

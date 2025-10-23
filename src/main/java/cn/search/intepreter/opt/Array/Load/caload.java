package cn.search.intepreter.opt.Array.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 240页
 */
public class caload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (int) frame.getOperandStack().pop();
        char[] array = (char[]) frame.getOperandStack().pop();
        int value = array[index];
        frame.getOperandStack().push(value);
        frame.getNextCode();
    }

}

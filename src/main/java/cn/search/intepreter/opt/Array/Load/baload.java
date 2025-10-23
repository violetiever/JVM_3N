package cn.search.intepreter.opt.Array.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 239页
 */
public class baload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (int) frame.getOperandStack().pop();
        byte[] array = (byte[]) frame.getOperandStack().pop();
        int value = array[index];
        frame.getOperandStack().push(value);
        frame.getNextCode();
    }

}

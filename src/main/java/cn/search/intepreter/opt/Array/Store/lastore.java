package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 297页
 */
public class lastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        long value = (long) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        long[] array = (long[]) frame.getOperandStack().pop();
        array[index] = value;
        frame.getNextCode();
    }

}

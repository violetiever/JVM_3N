package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 239页
 */
public class bastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value = (int) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        byte[] array = (byte[]) frame.getOperandStack().pop();
        array[index] = (byte) value;
        frame.getNextCode();
    }

}

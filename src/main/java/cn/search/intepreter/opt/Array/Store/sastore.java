package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 316页
 */
public class sastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        short value = (short) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        short[] array = (short[]) frame.getOperandStack().pop();
        array[index] = value;
        frame.getNextCode();
    }

}

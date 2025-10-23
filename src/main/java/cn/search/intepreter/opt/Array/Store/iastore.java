package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 270页
 */
public class iastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value = (int) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        int[] array = (int[]) frame.getOperandStack().pop();
        array[index] = value;
        frame.getNextCode();
    }

}

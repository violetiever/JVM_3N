package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 245页
 */
public class dastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        double value = (double) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        double[] array = (double[]) frame.getOperandStack().pop();
        array[index] = value;
        frame.getNextCode();
    }

}

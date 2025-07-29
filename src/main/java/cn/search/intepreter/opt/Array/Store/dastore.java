package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

/**
 * Java虚拟机规范.Java SE 8版 245页
 */
public class dastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        double value = (double) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        int arrayRef = (int) frame.getOperandStack().pop();
        double[] array = (double[]) Heap.getObjectFromPool(arrayRef);
        array[index] = value;
    }

}

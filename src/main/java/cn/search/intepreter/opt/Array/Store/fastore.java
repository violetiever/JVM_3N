package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

/**
 * Java虚拟机规范.Java SE 8版 258页
 */
public class fastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        float value = (float) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        int arrayRef = (int) frame.getOperandStack().pop();
        float[] array = (float[]) Heap.getObjectFromPool(arrayRef);
        array[index] = value;
    }

}

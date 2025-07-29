package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

/**
 * Java虚拟机规范.Java SE 8版 241页
 */
public class castore implements Opcode {

    @Override
    public void opt(Frame frame) {
        char value = (char) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        int arrayRef = (int) frame.getOperandStack().pop();
        char[] array = (char[]) Heap.getObjectFromPool(arrayRef);
        array[index] = value;
    }

}

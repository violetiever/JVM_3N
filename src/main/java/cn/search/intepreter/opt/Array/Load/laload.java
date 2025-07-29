package cn.search.intepreter.opt.Array.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

/**
 * Java虚拟机规范.Java SE 8版 296页
 */
public class laload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (int) frame.getOperandStack().pop();
        int arrayRef = (int) frame.getOperandStack().pop();
        long[] array = (long[]) Heap.getObjectFromPool(arrayRef);
        long value = array[index];
        frame.getOperandStack().push(value);
    }

}

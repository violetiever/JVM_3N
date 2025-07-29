package cn.search.intepreter.opt.Array.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

/**
 * Java虚拟机规范.Java SE 8版 316页
 */
public class saload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (int) frame.getOperandStack().pop();
        int arrayRef = (int) frame.getOperandStack().pop();
        short[] array = (short[]) Heap.getObjectFromPool(arrayRef);
        int value = array[index] & 0xFFFF;
        frame.getOperandStack().push(value);
    }

}

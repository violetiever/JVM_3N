package cn.search.intepreter.opt.A.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

/**
 * Java虚拟机规范.Java SE 8版 236页
 */
public class astore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        astoreBasic(frame, index);
        frame.getNextCode();
    }

    public static void astoreBasic(Frame frame, int index) {
        Object object = frame.getOperandStack().pop();
        Integer value = Heap.putIntoObjectPool(object);
        frame.getLocalVariable()[index] = value.longValue();
    }

}

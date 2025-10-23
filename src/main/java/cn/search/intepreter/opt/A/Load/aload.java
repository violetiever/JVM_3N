package cn.search.intepreter.opt.A.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

/**
 * Java虚拟机规范.Java SE 8版 234页
 */
public class aload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        aloadBasic(frame, index);
        frame.getNextCode();
    }

    public static void aloadBasic(Frame frame, int index) {
        int value = frame.getLocalVariable()[index].intValue();
        Object objectFromPool = Heap.getObjectFromPool(value);
        frame.getOperandStack().push(objectFromPool);
    }

}

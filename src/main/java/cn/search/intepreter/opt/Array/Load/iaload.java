package cn.search.intepreter.opt.Array.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 270页
 */
public class iaload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (int) frame.getOperandStack().pop();
        int[] array = (int[]) frame.getOperandStack().pop();
        int value = array[index];
        frame.getOperandStack().push(value);
        frame.getNextCode();
    }

}

package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 241页
 */
public class castore implements Opcode {

    @Override
    public void opt(Frame frame) {
        int intValue = (int) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        char[] array = (char[]) frame.getOperandStack().pop();
        array[index] = (char) intValue;
        frame.getNextCode();
    }

}

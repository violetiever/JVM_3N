package cn.search.intepreter.opt.Array.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 258页
 */
public class fastore implements Opcode {

    @Override
    public void opt(Frame frame) {
        float value = (float) frame.getOperandStack().pop();
        int index = (int) frame.getOperandStack().pop();
        float[] array = (float[]) frame.getOperandStack().pop();
        array[index] = value;
        frame.getNextCode();
    }

}

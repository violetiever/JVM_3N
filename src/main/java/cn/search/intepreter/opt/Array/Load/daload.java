package cn.search.intepreter.opt.Array.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 245页
 */
public class daload implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (int) frame.getOperandStack().pop();
        double[] array = (double[]) frame.getOperandStack().pop();
        double value = array[index];
        frame.getOperandStack().push(value);
        frame.getNextCode();
    }

}

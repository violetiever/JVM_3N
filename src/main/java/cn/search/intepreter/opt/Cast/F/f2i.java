package cn.search.intepreter.opt.Cast.F;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 256页
 */
public class f2i implements Opcode {

    @Override
    public void opt(Frame frame) {
        float value = (float) frame.getOperandStack().pop();
        int result = (int) value;
        frame.getOperandStack().push(result);
        frame.getNextCode();
    }

}

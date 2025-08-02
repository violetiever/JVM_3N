package cn.search.intepreter.opt.Cast.D;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 242页
 */
public class d2f implements Opcode {

    @Override
    public void opt(Frame frame) {
        double value = (double) frame.getOperandStack().pop();
        float result = (float) value;
        frame.getOperandStack().push(result);
    }

}

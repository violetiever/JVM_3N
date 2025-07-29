package cn.search.intepreter.opt.Math.F;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 262页
 */
public class fneg implements Opcode {

    @Override
    public void opt(Frame frame) {
        float value = (float) frame.getOperandStack().pop();
        frame.getOperandStack().push(-value);
    }

}

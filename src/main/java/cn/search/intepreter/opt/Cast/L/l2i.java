package cn.search.intepreter.opt.Cast.L;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 295页
 */
public class l2i implements Opcode {

    @Override
    public void opt(Frame frame) {
        long value = (long) frame.getOperandStack().pop();
        int result = (int) value;
        frame.getOperandStack().push(result);
    }

}

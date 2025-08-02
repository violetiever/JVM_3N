package cn.search.intepreter.opt.Cast.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 267页
 */
public class i2c implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value = (int) frame.getOperandStack().pop();
        int result = (byte) (value & 0xFF);
        frame.getOperandStack().push(result);
    }

}

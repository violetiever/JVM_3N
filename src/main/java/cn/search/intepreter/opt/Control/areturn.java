package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 235页
 */
public class areturn implements Opcode {

    @Override
    public void opt(Frame frame) {
        int value = (int) frame.getOperandStack().pop();
        frame = frame.getPreFrame();
        frame.getOperandStack().push(value);
    }

}

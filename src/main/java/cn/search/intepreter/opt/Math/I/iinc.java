package cn.search.intepreter.opt.Math.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 275页
 */
public class iinc implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index =  frame.getNextCode();
        int _const =  frame.getNextCode();
        int value = frame.getLocalVariable()[index].intValue();
        int result = value + _const;
        frame.getLocalVariable()[index] = (long) result;
        frame.getNextCode();
    }

}

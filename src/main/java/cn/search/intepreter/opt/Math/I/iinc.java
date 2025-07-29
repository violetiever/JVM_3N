package cn.search.intepreter.opt.Math.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 275页
 */
public class iinc implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index =  frame.getCode()[pc + 1].getValue();
        int _const =  frame.getCode()[pc + 2].getValue();
        int value = frame.getLocalVariable()[index].intValue();
        int result = value + _const;
        frame.getCode()[pc + 1].setValue(result);
    }

}

package cn.search.intepreter.opt.D.Const;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 247页
 */
public class dconst_1 implements Opcode {

    @Override
    public void opt(Frame frame) {
        frame.getOperandStack().push(1D);
    }

}

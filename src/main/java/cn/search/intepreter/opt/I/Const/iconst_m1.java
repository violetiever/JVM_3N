package cn.search.intepreter.opt.I.Const;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 271页
 */
public class iconst_m1 implements Opcode {

    @Override
    public void opt(Frame frame) {
        frame.getOperandStack().push(-1);
    }

}

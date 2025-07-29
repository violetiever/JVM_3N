package cn.search.intepreter.opt.L.Const;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 297页
 */
public class lconst_0 implements Opcode {

    @Override
    public void opt(Frame frame) {
        frame.getOperandStack().push(0L);
    }

}

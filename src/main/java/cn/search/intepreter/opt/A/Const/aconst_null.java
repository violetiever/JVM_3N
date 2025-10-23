package cn.search.intepreter.opt.A.Const;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 233页
 */
public class aconst_null implements Opcode {

    @Override
    public void opt(Frame frame) {
        frame.getOperandStack().push(null);
        frame.getNextCode();
    }

}

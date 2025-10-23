package cn.search.intepreter.opt.F.Const;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 259页
 */
public class fconst_0 implements Opcode {

    @Override
    public void opt(Frame frame) {
        frame.getOperandStack().push(0F);
        frame.getNextCode();
    }

}

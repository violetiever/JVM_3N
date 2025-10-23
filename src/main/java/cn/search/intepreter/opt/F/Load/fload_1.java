package cn.search.intepreter.opt.F.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 260页
 */
public class fload_1 implements Opcode {

    @Override
    public void opt(Frame frame) {
        fload.floadBasic(frame, 1);
        frame.getNextCode();
    }

}

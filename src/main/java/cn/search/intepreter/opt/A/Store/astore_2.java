package cn.search.intepreter.opt.A.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 237页
 */
public class astore_2 implements Opcode {

    @Override
    public void opt(Frame frame) {
        astore.astoreBasic(frame, 2);
        frame.getNextCode();
    }

}

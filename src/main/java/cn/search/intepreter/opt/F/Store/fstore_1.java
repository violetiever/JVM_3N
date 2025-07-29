package cn.search.intepreter.opt.F.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 264页
 */
public class fstore_1 implements Opcode {

    @Override
    public void opt(Frame frame) {
        fstore.fstoreBasic(frame, 1);
    }

}

package cn.search.intepreter.opt.D.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 252页
 */
public class dstore_0 implements Opcode {

    @Override
    public void opt(Frame frame) {
        dstore.dstoreBasic(frame, 0);
        frame.getNextCode();
    }

}

package cn.search.intepreter.opt.I.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 292页
 */
public class istore_1 implements Opcode {

    @Override
    public void opt(Frame frame) {
        istore.istoreBasic(frame, 1);
    }

}

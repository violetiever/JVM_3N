package cn.search.intepreter.opt.L.Store;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 306页
 */
public class lstore_2 implements Opcode {

    @Override
    public void opt(Frame frame) {
        lstore.lstoreBasic(frame, 2);
        frame.getNextCode();
    }

}

package cn.search.intepreter.opt.Compare.F;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 258页
 */
public class fcmpg implements Opcode {

    @Override
    public void opt(Frame frame) {
        fcmpl.fcmpBasic(frame, 1);
        frame.getNextCode();
    }

}

package cn.search.intepreter.opt.Control;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 315页
 */
public class ret implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        frame.setPc(frame.getLocalVariable()[index].intValue());
    }

}

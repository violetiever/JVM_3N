package cn.search.intepreter.opt;

import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 312页
 */
public class nop implements Opcode {

    @Override
    public void opt(Frame frame) {
        frame.getNextCode();
    }

}

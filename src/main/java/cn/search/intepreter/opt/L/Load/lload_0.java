package cn.search.intepreter.opt.L.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 301页
 */
public class lload_0 implements Opcode {

    @Override
    public void opt(Frame frame) {
        lload.lloadBasic(frame, 0);
    }

}

package cn.search.intepreter.opt.D.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 248页
 */
public class dload_1 implements Opcode {

    @Override
    public void opt(Frame frame) {
        dload.dloadBasic(frame, 1);
    }

}

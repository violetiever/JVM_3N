package cn.search.intepreter.opt.I.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 276页
 */
public class iload_2 implements Opcode {

    @Override
    public void opt(Frame frame) {
        iload.iloadBasic(frame, 2);
    }

}

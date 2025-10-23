package cn.search.intepreter.opt.A.Load;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 234页
 */
public class aload_3 implements Opcode {

    @Override
    public void opt(Frame frame) {
        aload.aloadBasic(frame, 3);
        frame.getNextCode();
    }

}

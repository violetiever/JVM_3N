package cn.search.intepreter.opt.Compare.D;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 246页
 */
public class dcmpg implements Opcode {

    @Override
    public void opt(Frame frame) {
        dcmpl.dcmpBasic(frame, 1);
    }

}

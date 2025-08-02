package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 299页
 */
public class ldc_w implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (frame.getNextCode().getValue() << 8) | (frame.getNextCode().getValue());
        ldc.ldcBasic(frame, index);
    }

}

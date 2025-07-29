package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 299页
 */
public class ldc_w implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = (frame.getCode()[pc + 1].getValue() << 8) | (frame.getCode()[pc + 2].getValue());
        ldc.ldcBasic(frame, index);
        frame.setPc(pc + 2);
    }

}

package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 299页
 */
public class ldc_w implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = CommonUtil.parseIndexByte(frame.getNextCode(), frame.getNextCode());
        ldc.ldcBasic(frame, index);
        frame.getNextCode();
    }

}

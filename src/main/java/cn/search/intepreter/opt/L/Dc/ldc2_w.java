package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantDoubleInfo;
import cn.search.reader.Clazz.CpInfo.ConstantLongInfo;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 300页
 */
public class ldc2_w implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = CommonUtil.parseIndexByte(frame.getNextCode(), frame.getNextCode());
        ConstantCpInfo constantCpInfo = frame.getRuntimeConstantPool()[index];
        Object value = null;
        if (constantCpInfo instanceof ConstantLongInfo) {
            value = ((ConstantLongInfo) constantCpInfo).getLongValue();
        } else if (constantCpInfo instanceof ConstantDoubleInfo) {
            value = ((ConstantDoubleInfo) constantCpInfo).getDoubleValue();
        }
        frame.getOperandStack().push(value);
        frame.getNextCode();
    }

}

package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantDoubleInfo;
import cn.search.reader.Clazz.CpInfo.ConstantLongInfo;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 300页
 */
public class ldc2_w implements Opcode {

    @Override
    public void opt(Frame frame) {
        int pc = frame.getPc();
        int index = (frame.getCode()[pc + 1].getValue() << 8) | (frame.getCode()[pc + 2].getValue());
        ConstantCpInfo constantCpInfo = frame.getRuntimeConstantPool()[index];
        Object temp = null;
        if(constantCpInfo instanceof ConstantLongInfo){
            temp = ((ConstantLongInfo) constantCpInfo).getLongValue();
        }else if(constantCpInfo instanceof ConstantDoubleInfo){
            temp = ((ConstantDoubleInfo) constantCpInfo).getDoubleValue();
        }
        frame.getOperandStack().push(temp);
        frame.setPc(pc + 2);
    }

}

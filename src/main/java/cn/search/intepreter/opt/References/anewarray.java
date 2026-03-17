package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 235页
 */
public class anewarray implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = CommonUtil.parseIndexByte(frame.getNextCode(), frame.getNextCode());
        int count = (int) frame.getOperandStack().pop();
        ConstantClassInfo constantClassInfo = (ConstantClassInfo) frame.getRuntimeConstantPool()[index];
        constantClassInfo.resolve();
        Object[] objectArray = new Object[count];
        frame.getOperandStack().push(objectArray);
        frame.getNextCode();
    }

}

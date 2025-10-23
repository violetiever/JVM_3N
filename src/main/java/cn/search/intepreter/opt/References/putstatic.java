package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantFieldRefInfo;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 314页
 */
public class putstatic implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        Object value = frame.getOperandStack().pop();
        ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) frame.getRuntimeConstantPool()[index];
        constantFieldRefInfo.resolve();
        FieldInfo fieldInfo = constantFieldRefInfo.getFieldInfo();
        fieldInfo.getThisClazz().initialize(frame);
        fieldInfo.setFieldValue(value);
        frame.getNextCode();
    }

}

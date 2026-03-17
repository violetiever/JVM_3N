package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantFieldRefInfo;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;

import java.lang.reflect.Field;

/**
 * Java虚拟机规范.Java SE 8版 265页
 */
public class getfield implements Opcode {

    @Override
    public void opt(Frame frame) throws NoSuchFieldException, IllegalAccessException {
        int index = CommonUtil.parseIndexByte(frame.getNextCode(), frame.getNextCode());
        Object objectRef = frame.getOperandStack().pop();
        ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) frame.getRuntimeConstantPool()[index];
        constantFieldRefInfo.resolve();
        FieldInfo fieldInfo = constantFieldRefInfo.getFieldInfo();
        // 通过反射获取需要获取值的字段
        Class object = fieldInfo.getThisClazz().transToClass();
        Field declaredField = object.getDeclaredField(fieldInfo.getName().getUtf8Info());
        declaredField.setAccessible(true);
        Object value = declaredField.get(objectRef);
        frame.getOperandStack().push(value);
        frame.getNextCode();
    }

}

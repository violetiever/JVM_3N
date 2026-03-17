package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantFieldRefInfo;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

import java.lang.reflect.Field;

/**
 * Java虚拟机规范.Java SE 8版 313页
 */
public class putfield implements Opcode {

    @Override
    public void opt(Frame frame) throws NoSuchFieldException, IllegalAccessException {
        int index = CommonUtil.parseIndexByte(frame.getNextCode(), frame.getNextCode());
        Object value = frame.getOperandStack().pop();
        Object objectRef = frame.getOperandStack().pop();
        ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) frame.getRuntimeConstantPool()[index];
        constantFieldRefInfo.resolve();
        FieldInfo fieldInfo = constantFieldRefInfo.getFieldInfo();
        // 通过反射获取需要设置值的字段
        Class object = fieldInfo.getThisClazz().transToClass();
        Field declaredField = object.getDeclaredField(fieldInfo.getName().getUtf8Info());
        declaredField.setAccessible(true);
        declaredField.set(objectRef, value);
        frame.getNextCode();
    }

}

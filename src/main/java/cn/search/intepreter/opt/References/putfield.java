package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantFieldRefInfo;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.runtime.Frame;
import cn.search.runtime.Heap;

import java.lang.reflect.Field;

/**
 * Java虚拟机规范.Java SE 8版 313页
 */
public class putfield implements Opcode {

    @Override
    public void opt(Frame frame) throws NoSuchFieldException, IllegalAccessException {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        Object value = frame.getOperandStack().pop();
        int objectRef = (int) frame.getOperandStack().pop();
        ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) frame.getRuntimeConstantPool()[index];
        constantFieldRefInfo.resolve();
        FieldInfo fieldInfo = constantFieldRefInfo.getFieldInfo();
        Object object = Heap.OBJECT_POOL.get(objectRef);
        // 通过反射获取需要设置值的字段
        Field declaredField = object.getClass().getDeclaredField(fieldInfo.getName().getUtf8Info());
        declaredField.set(object, value);
        frame.getNextCode();
    }

}

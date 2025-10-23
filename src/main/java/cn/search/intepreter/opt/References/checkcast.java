package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.runtime.Frame;

import java.util.Objects;

/**
 * Java虚拟机规范.Java SE 8版 241页
 */
public class checkcast implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        Object object = frame.getOperandStack().pop();
        frame.getOperandStack().push(object);
        ConstantClassInfo constantClassInfo = (ConstantClassInfo) frame.getRuntimeConstantPool()[index];
        constantClassInfo.resolve();
        if (Objects.isNull(object)) return;
        Clazz objectClazz = ClazzLoader.loadClazzByLoader(object.getClass().getName(), frame.getCurrentClazz().getClazzLoader());
        Clazz clazzInfo = constantClassInfo.getClazzInfo();
        if (!objectClazz.inInstanceOf(clazzInfo))
            throw new ClassCastException();
        frame.getNextCode();
    }

}

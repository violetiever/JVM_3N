package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.runtime.Frame;

import java.util.Objects;

/**
 * Java虚拟机规范.Java SE 8版 277页
 */
public class _instanceof implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        Object object = frame.getOperandStack().pop();
        int result = 0;
        ConstantClassInfo constantClassInfo = (ConstantClassInfo) frame.getRuntimeConstantPool()[index];
        constantClassInfo.resolve();
        if (Objects.nonNull(object)) {
            Clazz objectClazz = frame.getCurrentClazz().getClazzLoader().loadClazz(object.getClass().getName());
            Clazz clazzInfo = constantClassInfo.getClazzInfo();
            if (Objects.nonNull(objectClazz) && Objects.nonNull(clazzInfo) && objectClazz.inInstanceOf(clazzInfo))
                result = 1;
        }
        frame.getOperandStack().push(result);
    }

}

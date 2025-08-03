package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantFieldRefInfo;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import cn.search.reader.Utils.DescriptorUtil;
import cn.search.runtime.Frame;

import java.util.Objects;

/**
 * Java虚拟机规范.Java SE 8版 266页
 */
public class getstatic implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = (frame.getNextCode().getValue() << 8) | (frame.getNextCode().getValue());
        ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) frame.getRuntimeConstantPool()[index];
        String clazzName = constantFieldRefInfo.getClazz().getName().getUtf8Info();
        String fieldName = constantFieldRefInfo.getNameAndType().getName().getUtf8Info();
        String descriptor = constantFieldRefInfo.getNameAndType().getDescriptor().getUtf8Info();
        Clazz clazz = frame.getCurrentClazz().getClazzLoader().loadClazz(clazzName);

        if(Objects.isNull(clazz))
            clazz = AppClazzLoader.getInstance().loadClazz(clazzName);

        Integer objectRef = null;

        for (FieldInfo field : clazz.getFields()) {
            if(fieldName.equals(field.getName().getUtf8Info()) && descriptor.equals(field.getDescriptor().getUtf8Info())){
                if(Objects.isNull(field.getObjectRef())){
                    Clazz fieldClazz = DescriptorUtil.getClazzBySingleDescriptor(descriptor, clazz.getClazzLoader());
                }
                objectRef = field.getObjectRef();
            }
        }

    }

}

package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.*;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.runtime.Frame;
import cn.search.runtime.N3Object;

/**
 * Java虚拟机规范.Java SE 8版 298页
 */
public class ldc implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode();
        ldcBasic(frame, index);
        frame.getNextCode();
    }

    public static void ldcBasic(Frame frame, int index) {
        ConstantCpInfo constantCpInfo = frame.getRuntimeConstantPool()[index - 1];
        Object temp = null;
        if (constantCpInfo instanceof ConstantIntegerInfo) {
            temp = ((ConstantIntegerInfo) constantCpInfo).getIntegerValue().intValue();
        } else if (constantCpInfo instanceof ConstantFloatInfo) {
            temp = ((ConstantFloatInfo) constantCpInfo).getFloatValue();
        } else if (constantCpInfo instanceof ConstantStringInfo) {
            Clazz stringClazz = ClazzLoader.loadClazzByLoader("java/lang/String", frame.getCurrentClazz().getClazzLoader());
            N3Object newString = stringClazz.getNewInstance();
            newString.getFieldByNameAndDescriptor("value","[C").setFieldValue(((ConstantStringInfo) constantCpInfo).getString().getUtf8Info().toCharArray());
            temp = newString;
        } else if (constantCpInfo instanceof ConstantUtf8Info) {
            temp = ((ConstantUtf8Info) constantCpInfo).getUtf8Info();
        } else if (constantCpInfo instanceof ConstantClassInfo) {
            ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantCpInfo;
            temp = AppClazzLoader.getInstance().loadClazz(constantClassInfo.getName().getUtf8Info()).transToClass();
        } else if (constantCpInfo instanceof ConstantMethodTypeInfo) {
            ConstantMethodTypeInfo constantMethodTypeInfo = (ConstantMethodTypeInfo) constantCpInfo;
            constantMethodTypeInfo.invoke();
            temp = constantMethodTypeInfo.getInvokeObject();
        } else if (constantCpInfo instanceof ConstantMethodHandleInfo) {
            ConstantMethodHandleInfo constantMethodHandleInfo = (ConstantMethodHandleInfo) constantCpInfo;
            constantMethodHandleInfo.invoke();
            temp = constantMethodHandleInfo.getInvokeObject();
        }
        frame.getOperandStack().push(temp);
    }

}

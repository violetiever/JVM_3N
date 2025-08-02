package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.invoke.MethodHandle;
import cn.search.intepreter.invoke.MethodType;
import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.*;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 298页
 */
public class ldc implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = frame.getNextCode().getValue();
        ldcBasic(frame, index);
    }

    public static void ldcBasic(Frame frame, int index) {
        ConstantCpInfo constantCpInfo = frame.getRuntimeConstantPool()[index];
        Object temp = null;
        if (constantCpInfo.getClass().isInstance(ConstantIntegerInfo.class)) {
            temp = ((ConstantIntegerInfo) constantCpInfo).getIntegerValue();
        } else if (constantCpInfo.getClass().isInstance(ConstantFloatInfo.class)) {
            temp = ((ConstantFloatInfo) constantCpInfo).getFloatValue();
        } else if (constantCpInfo.getClass().isInstance(ConstantStringInfo.class)) {
            temp = ((ConstantStringInfo) constantCpInfo).getIndex();
        } else if (constantCpInfo.getClass().isInstance(ConstantClassInfo.class)) {
            ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantCpInfo;
            temp = AppClazzLoader.getInstance().loadClazz(constantClassInfo.getName().getUtf8Info());
        } else if (constantCpInfo.getClass().isInstance(ConstantMethodTypeInfo.class)) {
            ConstantMethodTypeInfo constantMethodTypeInfo = (ConstantMethodTypeInfo) constantCpInfo;
            String descriptor = constantMethodTypeInfo.getDescriptor().getUtf8Info();
            temp = new MethodType(descriptor);
        } else if (constantCpInfo.getClass().isInstance(ConstantMethodHandleInfo.class)) {
            ConstantMethodHandleInfo constantMethodHandleInfo = (ConstantMethodHandleInfo) constantCpInfo;
            temp = MethodHandle.getMethodHandleByConstantRefInfo(constantMethodHandleInfo.getReference());
        }
        frame.getOperandStack().push(temp);
    }

}

package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.*;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import cn.search.runtime.Frame;

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
        ConstantCpInfo constantCpInfo = frame.getRuntimeConstantPool()[index];
        Object temp = null;
        if (constantCpInfo instanceof ConstantIntegerInfo) {
            temp = ((ConstantIntegerInfo) constantCpInfo).getIntegerValue();
        } else if (constantCpInfo instanceof ConstantFloatInfo) {
            temp = ((ConstantFloatInfo) constantCpInfo).getFloatValue();
        } else if (constantCpInfo instanceof ConstantStringInfo) {
            temp = ((ConstantStringInfo) constantCpInfo).getIndex();
        } else if (constantCpInfo instanceof ConstantUtf8Info) {
            temp = ((ConstantUtf8Info) constantCpInfo).getUtf8Info();
        } else if (constantCpInfo instanceof ConstantClassInfo) {
            ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantCpInfo;
            temp = AppClazzLoader.getInstance().loadClazz(constantClassInfo.getName().getUtf8Info());
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

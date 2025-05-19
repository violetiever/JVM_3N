package cn.search.intepreter.opt.L.Dc;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.*;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.RunTime;

public class ldc implements Opcode {

    @Override
    public void opt(RunTime runTime, ConstantCpInfo[] constantPool, U1[] code) {
        int pc = runTime.getPc();
        U1 index = code[pc + 1];
        ConstantCpInfo constantCpInfo = constantPool[index.getValue()];
        if (constantCpInfo.getClass().isInstance(ConstantIntegerInfo.class)) {
            runTime.getStackFrame().push(((ConstantIntegerInfo) constantCpInfo).getIntegerValue());
        } else if (constantCpInfo.getClass().isInstance(ConstantFloatInfo.class)) {
            runTime.getStackFrame().push(((ConstantFloatInfo) constantCpInfo).getFloatValue());
        } else if (constantCpInfo.getClass().isInstance(ConstantStringInfo.class)) {
            runTime.getStackFrame().push(((ConstantStringInfo) constantCpInfo).getIndex());
        } else if (constantCpInfo.getClass().isInstance(ConstantClassInfo.class)) {

        }
    }

}

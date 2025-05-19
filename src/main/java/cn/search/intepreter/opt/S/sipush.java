package cn.search.intepreter.opt.S;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.RunTime;

public class sipush implements Opcode {

    @Override
    public void opt(RunTime runTime, ConstantCpInfo[] constantPool, U1[] code) {
        int pc = runTime.getPc();
        U1 instantNumHigh = code[pc + 1];
        U1 instantNumLow = code[pc + 2];
        int instantNum = (short) instantNumHigh.getValue() << 8 | instantNumLow.getValue();
        runTime.getStackFrame().push(instantNum);
    }

}

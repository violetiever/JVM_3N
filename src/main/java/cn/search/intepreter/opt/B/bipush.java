package cn.search.intepreter.opt.B;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.RunTime;

public class bipush implements Opcode {

    @Override
    public void opt(RunTime runTime, ConstantCpInfo[] constantPool, U1[] code) {
        int pc = runTime.getPc();
        U1 instantNum = code[pc + 1];
        runTime.getStackFrame().push(instantNum.getValue());
    }

}

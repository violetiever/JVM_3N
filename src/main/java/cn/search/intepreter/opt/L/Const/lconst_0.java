package cn.search.intepreter.opt.L.Const;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.RunTime;

public class lconst_0 implements Opcode {

    @Override
    public void opt(RunTime runTime, ConstantCpInfo[] constantPool, U1[] code) {
        runTime.getStackFrame().push(0L);
    }

}

package cn.search.intepreter.opt.I.Const;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.RunTime;

public class iconst_m1 implements Opcode {

    @Override
    public void opt(RunTime runTime, ConstantCpInfo[] constantPool, U1[] code) {
        runTime.getStackFrame().push(-1);
    }

}

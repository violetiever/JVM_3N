package cn.search.intepreter.opt;

import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.RunTime;

public class nop implements Opcode {

    @Override
    public void opt(RunTime runTime, ConstantCpInfo[] constantPool, U1[] code) {

    }

}

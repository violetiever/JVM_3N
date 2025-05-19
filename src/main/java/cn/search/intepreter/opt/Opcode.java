package cn.search.intepreter.opt;

import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.runtime.RunTime;

public interface Opcode {

    void opt(RunTime runTime, ConstantCpInfo[] constantPool, U1[] code);

}

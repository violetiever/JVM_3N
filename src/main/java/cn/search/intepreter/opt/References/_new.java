package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 310页
 */
public class _new implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        ConstantClassInfo constantClassInfo = (ConstantClassInfo)frame.getRuntimeConstantPool()[index];
        Clazz clazz = ClazzLoader.loadClazzByLoader(constantClassInfo.getName().getUtf8Info(), frame.getCurrentClazz().getClazzLoader());
        clazz.prepare();
        clazz.resolve();
        // clazz.initialize(frame);
        frame.getOperandStack().push(clazz);
        frame.getNextCode();
    }

}

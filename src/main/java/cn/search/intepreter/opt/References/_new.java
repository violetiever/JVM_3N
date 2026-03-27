package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.reader.Utils.CommonUtil;
import cn.search.runtime.Frame;
import cn.search.runtime.N3Object;

import java.util.Objects;

/**
 * Java虚拟机规范.Java SE 8版 310页
 */
public class _new implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = CommonUtil.parseIndexByte(frame.getNextCode(), frame.getNextCode());
        ConstantClassInfo constantClassInfo = (ConstantClassInfo) frame.getRuntimeConstantPool()[index];
        Clazz clazz = ClazzLoader.loadClazzByLoader(constantClassInfo.getName().getUtf8Info(), frame.getCurrentClazz().getClazzLoader());
        clazz.prepare();
        clazz.resolve();
        N3Object newInstance = clazz.getNewInstance();
        frame.getOperandStack().push(newInstance);
        // clazz.initialize(frame);
//        try {
//            Class toClass = clazz.transToClass();
//            // 通过反射获取new对象
//            // todo 考虑其他方案
//            Object newInstance = toClass.newInstance();
//            frame.getOperandStack().push(newInstance);
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }

        frame.getNextCode();
    }

}

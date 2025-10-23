package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.AttributeInfo.CodeAttribute;
import cn.search.reader.Clazz.AttributeInfo.ExceptionTable;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.runtime.Frame;

import java.util.Objects;

/**
 * Java虚拟机规范.Java SE 8版 238页
 */
public class athrow implements Opcode {

    @Override
    public void opt(Frame frame) {
        Frame oriFrame = frame;
        Throwable throwable = (Throwable) frame.getOperandStack().pop();
        do {
            Clazz throwableClazz = frame.getCurrentClazz().getClazzLoader().loadClazz(throwable.getClass().getName());
            CodeAttribute codeAttribute = frame.getCurrentMethod().getCodeAttribute();
            ExceptionTable[] exceptionTables = codeAttribute.getExceptionTable();
            int pc = frame.getPc();
            ExceptionTable exceptionTable = null;
            for (ExceptionTable table : exceptionTables) {
                ConstantClassInfo catchClass = table.getCatchClass();
                if (table.getStartPc().getValue() <= pc && pc < table.getEndPc().getValue()) {
                    catchClass.resolve();
                    Clazz catchClazz = catchClass.getClazzInfo();
                    if (throwableClazz.isSonClazzOf(catchClazz)) {
                        exceptionTable = table;
                        break;
                    }
                }
            }
            if (Objects.nonNull(exceptionTable)) {
                frame.setPc(exceptionTable.getHandlerPc().getValue());
                frame.getOperandStack().clear();
                frame.getOperandStack().push(throwable);
                break;
            } else
                frame = frame.getPreFrame();
        } while (Objects.nonNull(frame));
        if (Objects.isNull(frame)) {
            System.out.println(throwable);
            oriFrame.printFrameStack();
            System.exit(0);
        }
        frame.getNextCode();
    }

}

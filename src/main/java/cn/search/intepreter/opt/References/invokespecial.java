package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantInterfaceMethodRefInfo;
import cn.search.reader.Clazz.CpInfo.ConstantMethodRefInfo;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.Utils.DescriptorUtil;
import cn.search.runtime.Frame;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 * Java虚拟机规范.Java SE 8版 283页
 */
@Slf4j
public class invokespecial implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        ConstantCpInfo constantCpInfo = frame.getRuntimeConstantPool()[index];
        constantCpInfo.resolve();
        MethodInfo methodInfo = null;
        if (constantCpInfo instanceof ConstantInterfaceMethodRefInfo)
            methodInfo = ((ConstantInterfaceMethodRefInfo) constantCpInfo).getMethodInfo();
        else if (constantCpInfo instanceof ConstantMethodRefInfo)
            methodInfo = ((ConstantMethodRefInfo) constantCpInfo).getMethodInfo();
        if (Objects.isNull(methodInfo)) return;
        Clazz[] parameterClazzArray = DescriptorUtil.getMethodParameterClazzArrayByMethodDescriptor(methodInfo.getDescriptor().getUtf8Info(), frame.getCurrentClazz().getClazzLoader());
        if (Objects.isNull(parameterClazzArray))
            return;
        if (Arrays.asList(methodInfo.getAccessFlag()).contains("ACC_NATIVE")) {
            _invoke.invokeNative(methodInfo, parameterClazzArray, frame.getOperandStack(), true);
        } else {
            // 新增栈帧执行方法
            Frame newFrame = new Frame(frame,
                    _invoke.getLocalVariableByParamClazzArray(new Long[methodInfo.getCodeAttribute().getMaxLocals().getValue()], parameterClazzArray, frame.getOperandStack(), true),
                    new Stack<>(),
                    methodInfo.getThisClazz().getConstantPool(),
                    methodInfo,
                    methodInfo.getCodeAttribute().getCode(),
                    methodInfo.getThisClazz());
            newFrame.execute();
        }
        frame.getNextCode();
    }

}

package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantInterfaceMethodRefInfo;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.Utils.DescriptorUtil;
import cn.search.runtime.Frame;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 * Java虚拟机规范.Java SE 8版 281页
 */
public class invokeinterface implements Opcode {

    @Override
    public void opt(Frame frame) {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo = (ConstantInterfaceMethodRefInfo) frame.getRuntimeConstantPool()[index];
        constantInterfaceMethodRefInfo.resolve();
        MethodInfo methodInfo = constantInterfaceMethodRefInfo.getMethodInfo();
        int count = frame.getNextCode();
        int value_0 = frame.getNextCode();
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

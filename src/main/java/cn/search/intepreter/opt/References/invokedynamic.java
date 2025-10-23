package cn.search.intepreter.opt.References;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.CpInfo.*;
import cn.search.runtime.Frame;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.Stack;

/**
 * Java虚拟机规范.Java SE 8版 278页
 */
public class invokedynamic implements Opcode {

    @Override
    public void opt(Frame frame) {
        Long[] localVariable = new Long[0];
        Stack<Object> operandStack = new Stack<>();
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        int value_0 = frame.getNextCode();
        value_0 = frame.getNextCode();
        ConstantInvokeDynamicInfo constantInvokeDynamicInfo = (ConstantInvokeDynamicInfo) frame.getRuntimeConstantPool()[index];
        constantInvokeDynamicInfo.resolve();
        // 如果已经获取过CallSite则直接使用
        if (Objects.nonNull(constantInvokeDynamicInfo.getCallSite())) {
            ConstantMethodHandleInfo bootstrapMethod = constantInvokeDynamicInfo.getBootstrapMethod().getBootstrapMethod();
            bootstrapMethod.invoke();
            ConstantMethodRefInfo bsm = (ConstantMethodRefInfo) bootstrapMethod.getReference();
            bsm.resolve();

            // 获取引导方法参数
            ConstantCpInfo[] bootstrapArgument = constantInvokeDynamicInfo.getBootstrapMethod().getBootstrapArgument();
            MethodHandles.Lookup caller = MethodHandles.lookup();
            String invokedName = constantInvokeDynamicInfo.getNameAndType().getName().getUtf8Info();
            MethodType invokedType = constantInvokeDynamicInfo.getNameAndType().getMethodType();

            // 压入参数
            operandStack.push(bootstrapMethod.getInvokeObject());
            operandStack.push(caller);
            operandStack.push(invokedName);
            operandStack.push(invokedType);
            // 压入变参
            for (ConstantCpInfo ba : bootstrapArgument) {
                ba.invoke();
                operandStack.push(ba.getInvokeObject());
            }

            // 生成引导方法的栈帧并执行
            Frame bsmFrame = new Frame(frame,
                    localVariable,
                    operandStack,
                    bsm.getMethodInfo().getThisClazz().getConstantPool(),
                    bsm.getMethodInfo(),
                    bsm.getMethodInfo().getCodeAttribute().getCode(),
                    bsm.getMethodInfo().getThisClazz());
            bsmFrame.execute();
            // 获取CallSite对象并执行目标方法
            CallSite callSite = (CallSite) frame.getOperandStack().pop();
            constantInvokeDynamicInfo.setCallSite(callSite);
        }
        MethodHandle methodHandle = constantInvokeDynamicInfo.getCallSite().dynamicInvoker();
        MethodType methodType = methodHandle.type();
        Object[] args = new Object[methodType.parameterCount()];
        for (int i = methodType.parameterCount() - 1; i >= 0; i--)
            args[i] = operandStack.pop();

        try {
            Object result = constantInvokeDynamicInfo.getCallSite().getTarget().invokeExact(args);
            frame.getOperandStack().push(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        frame.getNextCode();
    }

}

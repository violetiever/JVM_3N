package cn.search.intepreter.opt.References;

import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.Enum.SpecialClazzType;
import cn.search.runtime.Heap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Stack;

@Slf4j
public class _invoke {

    // 用反射调用本地方法
    public static void invokeNative(MethodInfo methodInfo, Clazz[] parameterClazzArray, Stack<Object> stack, boolean hasObj) {
        Class[] parameterClassArray = new Class[parameterClazzArray.length];
        for (int i = 0; i < parameterClazzArray.length; i++) {
            parameterClassArray[i] = parameterClazzArray[i].transToClass();
        }
        Class methodClass = methodInfo.getThisClazz().transToClass();
        if (parameterClassArray.length == 0) parameterClassArray = null;
        try {
            Method declaredMethod = methodClass.getDeclaredMethod(methodInfo.getName().getUtf8Info(), parameterClassArray);
            declaredMethod.setAccessible(true);
            InvokeArgs args = getArgsByParamClazzArray(parameterClazzArray, stack, hasObj);
            Object returnObj = declaredMethod.invoke(args.getObj(), args.getArgs());
            if (Objects.nonNull(returnObj)) stack.push(returnObj);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 根据方法参数描述符和操作数栈初始化本地变量表
    public static Long[] getLocalVariableByParamClazzArray(Long[] localVariable, Clazz[] paramClazzArray, Stack<Object> stack, boolean hasObj) {
        int localSize = getLocalSizeByParamClazzArray(paramClazzArray, hasObj);
        int end = hasObj ? 1 : 0;
        // 将栈中的参数放入本地变量表中
        for (int i = localSize - 1; i >= end; i--) {
            Object arg = stack.pop();
            if (arg instanceof Long)
                i--;
            else if (arg instanceof Double) {
                i--;
                arg = Double.doubleToLongBits((double) arg);
            } else if (arg instanceof Float)
                arg = Float.floatToIntBits((float) arg);
            else if (arg instanceof Integer)
                arg = ((Integer) arg).longValue();
            else
                arg = Heap.putIntoObjectPool(arg).longValue();
            // 都转换成long类型存入本地变量表
            localVariable[i] = (long) arg;
        }
        if (hasObj)
            localVariable[0] = Heap.putIntoObjectPool(stack.pop()).longValue();
        return localVariable;
    }

    // 根据方法参数描述符和操作数栈获取本地方法的参数
    public static InvokeArgs getArgsByParamClazzArray(Clazz[] paramClazzArray, Stack<Object> stack, boolean hasObj) {
        InvokeArgs invokeArgs = new InvokeArgs();
        int localSize = paramClazzArray.length;
        Object[] args = new Object[localSize];

        for (int i = localSize - 1; i >= 0; i--)
            args[i] = stack.pop();
        if (localSize == 0) args = null;
        invokeArgs.setArgs(args);
        if (hasObj)
            invokeArgs.setObj(stack.pop());
        return invokeArgs;
    }

    public static int getLocalSizeByParamClazzArray(Clazz[] paramClazzArray, boolean hasObj) {
        int localSize = 0;
        for (Clazz clazz : paramClazzArray) {
            if (clazz.getName().equals(SpecialClazzType.Long.getClazzName())
                    || clazz.getName().equals(SpecialClazzType.Double.getClazzName()))
                localSize++;
            localSize++;
        }
        if (hasObj)
            localSize++;
        return localSize;
    }

}

@Data
class InvokeArgs {

    private Object obj;

    private Object[] args;

}
package cn.search.intepreter.opt.Stack;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * Java虚拟机规范.Java SE 8版 253页
 */
public class dup implements Opcode {

    @Override
    public void opt(Frame frame) {
        Object value = frame.getOperandStack().pop();
        List<Object> valueList = new ArrayList<>();
        valueList.add(value);
        dupPush(frame, valueList, valueList);
    }

    // 按照顺序入栈
    public static void dupPush(Frame frame, List<Object> xValueList, List<Object> valueList) {
        for (int i = xValueList.size() - 1; i >= 0; i--)
            frame.getOperandStack().push(xValueList.get(i));
        for (int i = valueList.size() - 1; i >= 0; i--)
            frame.getOperandStack().push(valueList.get(i));
    }

    public static boolean isType1(Object value) {
        return !isType2(value);
    }

    public static boolean isType2(Object value) {
        return (value instanceof Long || value instanceof Double);
    }

}

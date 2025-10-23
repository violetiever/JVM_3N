package cn.search.intepreter.opt.Stack;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * Java虚拟机规范.Java SE 8版 253页
 */
public class dup_x2 implements Opcode {

    @Override
    public void opt(Frame frame) {
        Object value1 = frame.getOperandStack().pop();
        Object value2 = frame.getOperandStack().pop();
        List<Object> valueList = new ArrayList<>();
        List<Object> xValueList = new ArrayList<>();
        valueList.add(value1);
        valueList.add(value2);
        xValueList.add(value1);
        // 如果value2是分类1中的数据则需要多读取一个value3
        if (dup.isType1(value2))
            valueList.add(frame.getOperandStack().pop());
        dup.dupPush(frame, xValueList, valueList);
        frame.getNextCode();
    }

}

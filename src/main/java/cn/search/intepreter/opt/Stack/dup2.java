package cn.search.intepreter.opt.Stack;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * Java虚拟机规范.Java SE 8版 254页
 */
public class dup2 implements Opcode {

    @Override
    public void opt(Frame frame) {
        Object value1 = frame.getOperandStack().pop();
        List<Object> valueList = new ArrayList<>();
        valueList.add(value1);
        // 如果value1是分类1中的数据则需要多读取一个value2
        if (dup.isType1(value1))
            valueList.add(frame.getOperandStack().pop());

        dup.dupPush(frame, valueList, valueList);
    }

}

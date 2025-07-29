package cn.search.intepreter.opt.Stack;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * Java虚拟机规范.Java SE 8版 253页
 */
public class dup_x1 implements Opcode {

    @Override
    public void opt(Frame frame) {
        Object value1 = frame.getOperandStack().pop();
        Object value2 = frame.getOperandStack().pop();
        List<Object> xValueList = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        valueList.add(value1);
        valueList.add(value2);
        xValueList.add(value1);
        dup.dupPush(frame, xValueList, valueList);
    }

}

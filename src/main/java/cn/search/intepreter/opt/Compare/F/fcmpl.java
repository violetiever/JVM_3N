package cn.search.intepreter.opt.Compare.F;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 258页
 */
public class fcmpl implements Opcode {

    @Override
    public void opt(Frame frame) {
        fcmpBasic(frame, -1);
        frame.getNextCode();
    }

    public static void fcmpBasic(Frame frame, int nanResult) {
        float value2 = (float) frame.getOperandStack().pop();
        float value1 = (float) frame.getOperandStack().pop();
        int result;
        if (Float.isNaN(value1) || Float.isNaN(value2))
            result = nanResult;
        else
            result = value1 > value2 ? 1 : (value1 == value2 ? 0 : -1);
        frame.getOperandStack().push(result);
    }

}

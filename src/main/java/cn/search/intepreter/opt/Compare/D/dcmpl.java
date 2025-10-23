package cn.search.intepreter.opt.Compare.D;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

/**
 * Java虚拟机规范.Java SE 8版 246页
 */
public class dcmpl implements Opcode {

    @Override
    public void opt(Frame frame) {
        dcmpBasic(frame, -1);
        frame.getNextCode();
    }

    public static void dcmpBasic(Frame frame, int nanResult) {
        double value2 = (double) frame.getOperandStack().pop();
        double value1 = (double) frame.getOperandStack().pop();
        int result;
        if (Double.isNaN(value1) || Double.isNaN(value2))
            result = nanResult;
        else
            result = value1 > value2 ? 1 : (value1 == value2 ? 0 : -1);
        frame.getOperandStack().push(result);
    }

}

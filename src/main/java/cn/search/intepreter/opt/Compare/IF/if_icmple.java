package cn.search.intepreter.opt.Compare.IF;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.function.BiPredicate;

/**
 * Java虚拟机规范.Java SE 8版 272页
 */
public class if_icmple implements Opcode {

    @Override
    public void opt(Frame frame) {
        BiPredicate<Integer, Integer> biPredicate = (value1, value2) -> value1 <= value2;
        if_icmpeq.ifIcmpBasic(frame, biPredicate);
    }

}

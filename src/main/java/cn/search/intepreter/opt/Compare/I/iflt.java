package cn.search.intepreter.opt.Compare.I;

import cn.search.intepreter.opt.Opcode;
import cn.search.runtime.Frame;

import java.util.function.Predicate;

/**
 * Java虚拟机规范.Java SE 8版 273页
 */
public class iflt implements Opcode {

    @Override
    public void opt(Frame frame) {
        Predicate<Integer> predicate = value -> value < 0;
        ifeq.ifBasic(frame, predicate);
    }

}

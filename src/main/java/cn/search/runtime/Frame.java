package cn.search.runtime;

import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.Usinged.U1;
import lombok.Data;

import java.util.Stack;

/**
 * 栈帧
 */
@Data
public class Frame {

    /**
     * PC寄存器
     */
    private int pc = 0;

    /**
     * 上一帧
     */
    private Frame preFrame;

    /**
     * 本地变量表
     */
    private Long[] localVariable;

    /**
     * 操作数栈
     */
    private Stack<Object> operandStack;

    /**
     * 运行时常量池
     */
    private ConstantCpInfo[] runtimeConstantPool;

    /**
     * 当前的方法信息
     */
    private MethodInfo currentMethod;

    /**
     * 方法的code属性
     */
    private U1[] code;

    /**
     * 当前的类信息
     */
    private Clazz currentClazz;

    public U1 getNextCode() {
        this.pc++;
        return this.getCode()[pc];
    }

}

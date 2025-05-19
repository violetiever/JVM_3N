package cn.search.runtime;

import lombok.Data;

import java.util.Stack;

@Data
public class RunTime {

    /**
     * PC寄存器
     */
    private int pc = 0;

    /**
     * 局部变量表
     */
    private Long[] localTable;

    /**
     * 栈帧
     */
    private Stack<Object> stackFrame;

}

package cn.search.runtime;

import cn.search.intepreter.opt.Opcode;
import cn.search.intepreter.opt.OpcodeMap;
import cn.search.reader.Clazz.AttributeInfo.LineNumberTable;
import cn.search.reader.Clazz.AttributeInfo.LineNumberTableAttribute;
import cn.search.reader.Clazz.AttributeInfo.SourceFileAttribute;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.Usinged.U1;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * 栈帧
 */
@Data
@Slf4j
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
     * 如果是原始类型则转换成long类型后放入
     * 如果是对象则放入Heap内对象池的索引
     */
    private Long[] localVariable;

    /**
     * 操作数栈
     * 如果是原始类型则放入原始类型的包装类型
     * 如果是对象则放入对象实例
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

    public Frame(Frame preFrame, Long[] localVariable, Stack<Object> operandStack, ConstantCpInfo[] runtimeConstantPool, MethodInfo currentMethod, U1[] code, Clazz currentClazz) {
        this.preFrame = preFrame;
        this.localVariable = localVariable;
        this.operandStack = operandStack;
        this.runtimeConstantPool = runtimeConstantPool;
        this.currentMethod = currentMethod;
        this.code = code;
        this.currentClazz = currentClazz;
    }

    // 获取下一个code码
    public int getNextCode() {
        this.pc++;
        return this.getCode()[pc].getValue();
    }

    public byte getNextByteCode() {
        this.pc++;
        return Integer.valueOf(this.getCode()[pc].getValue()).byteValue();
    }

    // 新增执行方法，每个指令执行完毕后需指向下一个待执行的指令码
    public void execute() {
        while (this.pc <= this.code.length - 1) {
            Opcode opcode = OpcodeMap.OPCODE.get(this.code[pc].getValue());
            String name = opcode.getClass().getName();
            log.debug("开始执行方法 className = {} methodName = {} pc = {}  opcode = {} localVar = {} stack = {}",
                    this.currentClazz.getName(),
                    this.currentMethod.getName().getUtf8Info(),
                    this.pc,
                    name.substring(name.lastIndexOf(".") + 1),
                    this.localVariable,
                    this.operandStack);
            try {
                opcode.opt(this);
            } catch (Exception e) {
                log.error("execute opcode exception {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void printFrameStack() {
        LineNumberTableAttribute lineNumberTable = (LineNumberTableAttribute) this.currentMethod.getCodeAttribute().getAttributeByClass(LineNumberTableAttribute.class);
        int lineNumber = lineNumberTable.getLineNumberTable()[0].getLineNumber().getValue();
        for (LineNumberTable numberTable : lineNumberTable.getLineNumberTable()) {
            if (numberTable.getStartPc().getValue() <= this.pc) break;
            lineNumber = numberTable.getLineNumber().getValue();
        }
        System.out.println("    at " + this.currentClazz.getName() + "."
                + this.currentMethod.getName().getUtf8Info() + "("
                + ((SourceFileAttribute) this.currentClazz.getAttributeByClass(SourceFileAttribute.class)).getSourceFile().getUtf8Info()
                + ":" + lineNumber + ")");
        this.preFrame.printFrameStack();
    }

}

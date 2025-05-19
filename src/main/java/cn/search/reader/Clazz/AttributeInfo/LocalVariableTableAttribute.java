package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class LocalVariableTableAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 localVariableTableLength;

    // length = localVariableTableLength
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private LocalVariableTable[] localVariableTable;

    public LocalVariableTableAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.localVariableTable.length; i++) {
            this.localVariableTable[i].initConstant(constantPool);
        }

    }

}

@Data
@ClazzConstructor
class LocalVariableTable {

    // u2
    @ClazzField(order = 0)
    private U2 startPc;

    // u2
    @ClazzField(order = 1)
    private U2 length;

    // u2
    @ClazzField(order = 2)
    private U2 nameIndex;

    // u2
    @ClazzField(order = 3)
    private U2 descriptorIndex;

    // u2
    @ClazzField(order = 4)
    private U2 index;


    private ConstantUtf8Info name;

    private ConstantUtf8Info descriptor;

    public LocalVariableTable(DataInputStream dataInput) {

    }

    public void initConstant(ConstantCpInfo[] constantPool) {
        this.name = (ConstantUtf8Info) constantPool[this.nameIndex.getValue() - 1];
        this.descriptor = (ConstantUtf8Info) constantPool[this.descriptorIndex.getValue() - 1];

    }

}

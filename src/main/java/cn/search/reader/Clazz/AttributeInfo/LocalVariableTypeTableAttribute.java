package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
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
public class LocalVariableTypeTableAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 localVariableTypeTableLength;

    // length = localVariableTypeTableLength
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private LocalVariableTypeTable[] localVariableTypeTable;

    public LocalVariableTypeTableAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.localVariableTypeTable.length; i++) {
            this.localVariableTypeTable[i].initConstant(constantPool);
        }

    }

}

@Data
@ClazzConstructor
class LocalVariableTypeTable {

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
    private U2 signatureIndex;

    // u2
    @ClazzField(order = 4)
    private U2 index;

    private ConstantUtf8Info name;

    private ConstantUtf8Info signature;

    public LocalVariableTypeTable(DataInputStream dataInput) {

    }

    public void initConstant(ConstantCpInfo[] constantPool) {
        this.name = (ConstantUtf8Info) constantPool[this.nameIndex.getValue() - 1];
        this.signature = (ConstantUtf8Info) constantPool[this.signatureIndex.getValue() - 1];

    }

}
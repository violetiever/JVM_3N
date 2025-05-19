package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ExceptionsAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 numberOfExceptions;

    // length = numberOfExceptions each exceptionIndex is u2
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private U2[] exceptionIndexTable;

    private ConstantClassInfo[] exceptionTable;

    public ExceptionsAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        this.exceptionTable = new ConstantClassInfo[this.numberOfExceptions.getValue()];
        for (int i = 0; i < this.exceptionTable.length; i++) {
            this.exceptionTable[i] = (ConstantClassInfo) constantPool[exceptionIndexTable[i].getValue() - 1];
        }

    }



}

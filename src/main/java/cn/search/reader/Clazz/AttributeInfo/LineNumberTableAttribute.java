package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class LineNumberTableAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 lineNumberTableLength;

    // length = lineNumberTableLength
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private LineNumberTable[] lineNumberTable;

    public LineNumberTableAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {


    }

}

@Data
@ClazzConstructor
class LineNumberTable {

    // u2
    @ClazzField(order = 0)
    private U2 startPc;

    // u2
    @ClazzField(order = 1)
    private U2 lineNumber;

    public LineNumberTable(DataInputStream dataInput) {

    }

}

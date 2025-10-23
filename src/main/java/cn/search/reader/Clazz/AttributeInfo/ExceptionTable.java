package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Usinged.U2;
import lombok.Data;

import java.io.DataInputStream;

@Data
@ClazzConstructor
public class ExceptionTable {

    // u2
    @ClazzField(order = 0)
    private U2 startPc;

    // u2
    @ClazzField(order = 1)
    private U2 endPc;

    // u2
    @ClazzField(order = 2)
    private U2 handlerPc;

    // u2
    @ClazzField(order = 3)
    private U2 catchType;

    private ConstantClassInfo catchClass;

    public ExceptionTable(DataInputStream dataInput) {

    }

}

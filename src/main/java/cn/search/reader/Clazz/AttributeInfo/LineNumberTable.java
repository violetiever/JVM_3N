package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U2;
import lombok.Data;

import java.io.DataInputStream;

@Data
@ClazzConstructor
public class LineNumberTable {

    // u2
    @ClazzField(order = 0)
    private U2 startPc;

    // u2
    @ClazzField(order = 1)
    private U2 lineNumber;

    public LineNumberTable(DataInputStream dataInput) {

    }

}

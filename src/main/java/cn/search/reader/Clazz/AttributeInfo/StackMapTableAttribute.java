package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.AttributeInfo.StackMapFrame.StackMapFrame;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class StackMapTableAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 numberOfEntries;

    // length = numberOfEntries
    private StackMapFrame[] entries;

    public StackMapTableAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        this.entries = new StackMapFrame[numberOfEntries.getValue()];
        for (int i = 0; i < this.entries.length; i++) {
            entries[i] = StackMapFrame.getFrameByType(dataInput, constantPool);
        }

    }

}

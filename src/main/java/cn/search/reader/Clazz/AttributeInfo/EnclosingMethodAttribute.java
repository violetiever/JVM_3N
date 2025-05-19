package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class EnclosingMethodAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 classIndex;

    // u2
    @ClazzField(order = 1)
    private U2 methodIndex;

    private ConstantClassInfo clazz;

    public EnclosingMethodAttribute() {
    }

    public EnclosingMethodAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        if(this.methodIndex.getValue() != 0)
            this.clazz = (ConstantClassInfo) constantPool[this.methodIndex.getValue() -1];

    }

}

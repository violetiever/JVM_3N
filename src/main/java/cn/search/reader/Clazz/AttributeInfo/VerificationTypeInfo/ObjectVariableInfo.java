package cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo;

import cn.search.Annotation.ClazzConstructor;
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
public class ObjectVariableInfo extends VerificationTypeInfo {

    // tag = ITEM_Object 7

    // u2
    @ClazzField(order = 0)
    private U2 cpoolIndex;

    private ConstantClassInfo clazz;

    public ObjectVariableInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        this.clazz = (ConstantClassInfo) constantPool[cpoolIndex.getValue() - 1];

    }

}

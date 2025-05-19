package cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class UninitializedVariableInfo extends VerificationTypeInfo{

    // tag = ITEM_Uninitialized 8

    // u2
    @ClazzField(order = 0)
    private U2 offset;

    public UninitializedVariableInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

}

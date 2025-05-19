package cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class NullVariableInfo extends VerificationTypeInfo{

    // tag = ITEM_Null 5


    public NullVariableInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

}

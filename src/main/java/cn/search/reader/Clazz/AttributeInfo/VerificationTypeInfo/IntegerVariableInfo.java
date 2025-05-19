package cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class IntegerVariableInfo extends VerificationTypeInfo{

    // tag = ITEM_Integer 1


    public IntegerVariableInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

}

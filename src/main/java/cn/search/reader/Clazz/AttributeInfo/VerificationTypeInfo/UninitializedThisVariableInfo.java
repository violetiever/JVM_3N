package cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class UninitializedThisVariableInfo extends VerificationTypeInfo{

    // tag = ITEM_UninitializedThis 6


    public UninitializedThisVariableInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

}

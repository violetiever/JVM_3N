package cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class DoubleVariableInfo extends VerificationTypeInfo{

    // tag = ITEM_Double 3


    public DoubleVariableInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

}

package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class RuntimeInvisibleParameterAnnotationsAttribute extends AttributeInfo {

    // u1
    @ClazzField(order = 0)
    private U1 numParameters;

    // length = numParameters
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private ParameterAnnotations[] parameterAnnotations;


    public RuntimeInvisibleParameterAnnotationsAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.parameterAnnotations.length; i++) {
            this.parameterAnnotations[i].initConstant(constantPool);
        }
    }

}

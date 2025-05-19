package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class RuntimeVisibleParameterAnnotationsAttribute extends AttributeInfo {

    // u1
    @ClazzField(order = 0)
    private U1 numParameters;

    // length = numParameters
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private ParameterAnnotations[] parameterAnnotations;

    public RuntimeVisibleParameterAnnotationsAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.parameterAnnotations.length; i++) {
            this.parameterAnnotations[i].initConstant(constantPool);
        }
    }

}

@Data
@ClazzConstructor
class ParameterAnnotations {

    // u2
    @ClazzField(order = 0)
    private U2 numAnnotations;

    // length = numAnnotations
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private Annotation[] annotations;

    public ParameterAnnotations(DataInputStream dataInput) {

    }

    public void initConstant(ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.annotations.length; i++) {
            this.annotations[i].initConstant(constantPool);
        }
    }

}


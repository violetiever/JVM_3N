package cn.search.reader.Clazz.AttributeInfo;

import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnnotationDefaultAttribute extends AttributeInfo {

    // only one
    private ElementValue defaultValue;

    public AnnotationDefaultAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool){

        this.defaultValue = ElementValue.getElementValueByTag(dataInput);
        if(Objects.nonNull( this.defaultValue))
            this.defaultValue.initConstant(constantPool);

    }

}

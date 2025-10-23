package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class BootstrapMethodsAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 numBootstrapMethods;

    // length = numBootstrapMethods
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private BootstrapMethods[] bootstrapMethods;

    public BootstrapMethodsAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {
        for (int i = 0; i < this.bootstrapMethods.length; i++)
            this.bootstrapMethods[i].initConstant(constantPool);
    }

}

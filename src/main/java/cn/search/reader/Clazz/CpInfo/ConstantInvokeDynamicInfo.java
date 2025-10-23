package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.AttributeInfo.BootstrapMethods;
import cn.search.reader.Clazz.AttributeInfo.BootstrapMethodsAttribute;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.lang.invoke.CallSite;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantInvokeDynamicInfo extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 bootstrapMethodAttrIndex;

    // u2
    @ClazzField(order = 1)
    private U2 nameAndTypeIndex;

    @ClazzCpInfoInit(order = 2, initOrder = 1)
    private ConstantNameAndTypeInfo nameAndType;

    private BootstrapMethods bootstrapMethod;

    private CallSite callSite;

    public ConstantInvokeDynamicInfo(DataInputStream dataInput) {

    }

    @Override
    public void resolve() {
        BootstrapMethodsAttribute bootstrapMethodAttribute = (BootstrapMethodsAttribute) this.getThisClazz().getAttributeByClass(BootstrapMethodsAttribute.class);
        this.bootstrapMethod = bootstrapMethodAttribute.getBootstrapMethods()[this.bootstrapMethodAttrIndex.getValue()];
    }

    @Override
    public String toString() {
        return "ConstantInvokeDynamicInfo{" + "\n" +
                " nameAndType=" + nameAndType + "\n" +
                ", bootstrapMethod=" + bootstrapMethod + "\n" +
                '}';
    }

}

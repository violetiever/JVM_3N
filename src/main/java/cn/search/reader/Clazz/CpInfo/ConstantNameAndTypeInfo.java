package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.lang.invoke.MethodType;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantNameAndTypeInfo extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 nameIndex;

    // u2
    @ClazzField(order = 1)
    private U2 descriptorIndex;

    @ClazzCpInfoInit(order = 2, initOrder = 0)
    private ConstantUtf8Info name;

    @ClazzCpInfoInit(order = 3, initOrder = 1)
    private ConstantUtf8Info descriptor;

    public ConstantNameAndTypeInfo(DataInputStream dataInput) {

    }

    @Override
    public String toString() {
        return "ConstantNameAndTypeInfo{" + "\n" +
                "name=" + name + "\n" +
                ", descriptor=" + descriptor + "\n" +
                '}';
    }

    public MethodType getMethodType(){
        return MethodType.fromMethodDescriptorString(this.descriptor.getUtf8Info(), null);
    }

}

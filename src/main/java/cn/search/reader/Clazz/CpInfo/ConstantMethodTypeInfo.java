package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.lang.invoke.MethodType;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantMethodTypeInfo extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 descriptorIndex;

    @ClazzCpInfoInit(order = 1, initOrder = 0)
    private ConstantUtf8Info descriptor;

    public ConstantMethodTypeInfo(DataInputStream dataInput) {

    }

    // invokeObject的类型是MethodType
    @Override
    public void invoke() {
        if (Objects.isNull(this.invokeObject)) {
            MethodType methodType = MethodType.fromMethodDescriptorString(this.descriptor.getUtf8Info(), null);
            this.invokeObject = methodType;
        }
    }

}

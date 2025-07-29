package cn.search.intepreter.invoke;

import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Utils.DescriptorUtil;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MethodType {

    private Clazz[] parameterClazz;

    private Clazz returnClazz;

    private String methodDescriptor;

    public MethodType(String descriptor) {
        this.methodDescriptor = descriptor;
        this.parameterClazz = DescriptorUtil.getMethodParameterClazzArrayByMethodDescriptor(descriptor, null);
        this.returnClazz = DescriptorUtil.getReturnClazzByMethodDescriptor(descriptor, null);
    }



}

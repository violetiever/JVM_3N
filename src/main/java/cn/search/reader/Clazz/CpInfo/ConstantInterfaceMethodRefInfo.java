package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.reader.Utils.DescriptorUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantInterfaceMethodRefInfo extends ConstantRefInfo {

    private MethodInfo methodInfo;

    public ConstantInterfaceMethodRefInfo(DataInputStream dataInput) {
        super(dataInput);
    }

    // 解析
    public void resolve() {
        if (Objects.nonNull(this.methodInfo))
            return;
        String clazzName = this.getClazz().getName().getUtf8Info();
        String methodName = this.getNameAndType().getName().getUtf8Info();
        String descriptor = this.getNameAndType().getDescriptor().getUtf8Info();
        Clazz clazz = ClazzLoader.loadClazzByLoader(clazzName, this.getThisClazz().getClazzLoader());
        clazz.resolve();
        // 加载描述符中的所有类
        DescriptorUtil.getMethodParameterClazzArrayByMethodDescriptor(descriptor, this.getThisClazz().getClazzLoader());

        this.methodInfo = clazz.getMethodByNameAndDescriptor(methodName, descriptor);

        if (Objects.nonNull(this.methodInfo)) {
            clazz.resolve();
            return;
        }
        Clazz objectClazz = ClazzLoader.loadClazzByLoader("java.lang.Object", this.getThisClazz().getClazzLoader());
        this.methodInfo = objectClazz.getMethodByNameAndDescriptor(methodName, descriptor);

        if (Objects.nonNull(this.methodInfo)) {
            clazz.resolve();
            return;
        }

        // 查找类实现的接口，因为接口可以继承多个接口，需要进行递归查找
        this.methodInfo = getInterfaceMethodRecursion(clazz.getInterfacesInfo(), methodName, descriptor);
        if (Objects.nonNull(this.methodInfo))
            this.methodInfo.getThisClazz().resolve();
    }

}

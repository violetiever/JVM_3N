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
public class ConstantMethodRefInfo extends ConstantRefInfo {

    private MethodInfo methodInfo;

    public ConstantMethodRefInfo(DataInputStream dataInput) {
        super(dataInput);
    }

    // 解析
    public void resolve() {
        if (Objects.nonNull(this.methodInfo))
            return;

        String clazzName = this.getClazz().getName().getUtf8Info();
        String methodName = this.getNameAndType().getName().getUtf8Info();
        String descriptor = this.getNameAndType().getDescriptor().getUtf8Info();

        Clazz clazz = DescriptorUtil.getClazzBySingleDescriptor(clazzName, this.getThisClazz().getClazzLoader());
//        clazz.resolve();
        // 加载描述符中的所有类
        DescriptorUtil.getMethodParameterClazzArrayByMethodDescriptor(descriptor, this.getThisClazz().getClazzLoader());

        while (Objects.nonNull(clazz)) {
            // 对签名多态性进行特殊处理
            // 在Java SE8中只有java.lang.invoke.MethodHandle的invoke和invokeExact是签名多态方法
            // 如果是这两个方法则无视描述符解析到对应的方法中
            if ("java.lang.invoke.MethodHandle".equals(clazzName) &&
                    ("invoke".equals(methodName) || "invokeExact".equals(methodName))) {
                MethodInfo[] methodList = clazz.getMethodByName(methodName);
                if (Objects.nonNull(methodList)) this.methodInfo = methodList[0];
            } else
                this.methodInfo = clazz.getMethodByNameAndDescriptor(methodName, descriptor);

            if (Objects.nonNull(this.methodInfo)) {
                // todo 验证如果没找到方法的情况下类是否会触发解析过程
//                clazz.resolve();
                return;
            }

            // 查找类实现的接口
            this.methodInfo = getInterfaceMethodRecursion(clazz.getInterfacesInfo(), methodName, descriptor);
            if (Objects.nonNull(this.methodInfo)) {
//                this.methodInfo.getThisClazz().resolve();
                return;
            }

            if (Objects.nonNull(clazz.getSuperClassInfo())) {
                String superClazzName = clazz.getSuperClassInfo().getName().getUtf8Info();
                // 获取当前类的父类循环寻找字段
                clazz = ClazzLoader.loadClazzByLoader(superClazzName, this.getThisClazz().getClazzLoader());
            } else
                break;
        }

    }

    @Override
    public String toString() {
        return "ConstantMethodRefInfo{" + "\n" +
                "methodInfo=" + methodInfo + "\n" +
                '}';
    }

}

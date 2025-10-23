package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantRefInfo extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 classIndex;

    // u2
    @ClazzField(order = 1)
    private U2 nameAndTypeIndex;

    @ClazzCpInfoInit(order = 2, initOrder = 0)
    private ConstantClassInfo clazz;

    @ClazzCpInfoInit(order = 3, initOrder = 1)
    private ConstantNameAndTypeInfo nameAndType;

    public ConstantRefInfo() {

    }

    public ConstantRefInfo(DataInputStream dataInput) {

    }

    // 递归获取接口方法
    public MethodInfo getInterfaceMethodRecursion(ConstantClassInfo[] constantClassInfoList, String methodName, String descriptor) {
        if (Objects.nonNull(constantClassInfoList))
            for (ConstantClassInfo interfaceClass : constantClassInfoList) {
                Clazz interfaceClazz = ClazzLoader.loadClazzByLoader(interfaceClass.getName().getUtf8Info(), this.getThisClazz().getClazzLoader());
                MethodInfo method = interfaceClazz.getMethodByNameAndDescriptor(methodName, descriptor);
                if (Objects.nonNull(method)) {
                    List<String> methodAccessFlagList = Arrays.asList(method.getAccessFlag());
                    if (!methodAccessFlagList.contains("ACC_ABSTRACT")
                            || (!methodAccessFlagList.contains("ACC_PRIVATE") && !methodAccessFlagList.contains("ACC_STATIC")))
                        return method;
                }

                ConstantClassInfo[] interfacesInfo = interfaceClazz.getInterfacesInfo();
                if (Objects.nonNull(interfacesInfo)) {
                    getInterfaceMethodRecursion(interfacesInfo, methodName, descriptor);
                }
            }
        return null;
    }

    @Override
    public String toString() {
        return "ConstantRefInfo{" +
                "clazz=" + clazz +
                ", nameAndType=" + nameAndType +
                '}';
    }

}

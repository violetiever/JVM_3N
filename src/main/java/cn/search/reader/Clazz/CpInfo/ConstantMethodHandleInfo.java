package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantMethodHandleInfo extends ConstantCpInfo {

    // todo 需要解析这个类
    // u1
    @ClazzField(order = 0)
    private U1 referenceKind;

    // u2
    @ClazzField(order = 1)
    private U2 referenceIndex;

    @ClazzCpInfoInit(order = 2, initOrder = 1)
    private ConstantRefInfo reference;

    public ConstantMethodHandleInfo(DataInputStream dataInput) {

    }

    // invokeObject的类型是MethodHandle
    @Override
    public void invoke() {
        if (Objects.isNull(this.invokeObject)) {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            int referenceKindValue = referenceKind.getValue();
            reference.resolve();
            String clazzName = reference.getClazz().getName().getUtf8Info();
            String referenceName = reference.getNameAndType().getName().getUtf8Info();
            MethodHandle methodHandle = null;
            try {
                Class<?> cClass = ClassLoader.getSystemClassLoader().loadClass(clazzName);
                if (referenceKindValue <= 4) {
                    String fieldType = reference.getNameAndType().getDescriptor().getUtf8Info();
                    Class<?> fieldClass = ClassLoader.getSystemClassLoader().loadClass(fieldType);
                    if (referenceKindValue == 1)
                        methodHandle = lookup.findGetter(cClass, referenceName, fieldClass);
                    else if (referenceKindValue == 2)
                        methodHandle = lookup.findStaticGetter(cClass, referenceName, fieldClass);
                    else if (referenceKindValue == 3)
                        methodHandle = lookup.findSetter(cClass, referenceName, fieldClass);
                    else if (referenceKindValue == 4)
                        methodHandle = lookup.findStaticSetter(cClass, referenceName, fieldClass);
                } else {
                    MethodType methodType = reference.getNameAndType().getMethodType();
                    if (referenceKindValue == 5)
                        methodHandle = lookup.findVirtual(cClass, referenceName, methodType);
                    else if (referenceKindValue == 6)
                        methodHandle = lookup.findStatic(cClass, referenceName, methodType);
                    else if (referenceKindValue == 7)
                        methodHandle = lookup.findSpecial(cClass, referenceName, methodType, cClass);
                    else if (referenceKindValue == 8)
                        methodHandle = lookup.findConstructor(cClass, methodType);
                    else if (referenceKindValue == 9)
                        methodHandle = lookup.findVirtual(cClass, referenceName, methodType);
                }
                this.invokeObject = methodHandle;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "ConstantMethodHandleInfo{" + "\n" +
                "reference=" + reference + "\n" +
                '}';
    }

}

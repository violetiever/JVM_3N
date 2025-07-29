package cn.search.intepreter.invoke;

import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantNameAndTypeInfo;
import cn.search.reader.Clazz.CpInfo.ConstantRefInfo;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import cn.search.runtime.Heap;
import cn.search.runtime.MethodArea;
import lombok.Data;

import java.util.HashMap;
import java.util.Objects;

@Data
public class MethodHandle {

    private static HashMap<String, MethodHandle> METHOD_HANDLE_POOL = new HashMap<>();

    private MethodInfo methodInfo;

    private MethodType methodType;

    public MethodHandle() {
    }


    private static MethodInfo resolveMethod(Clazz clazz, String name, MethodType type) {
        // 如果方法名和描述符都相等则直接返回方法
        for (MethodInfo m : clazz.getMethods()) {
            if (m.getName().getUtf8Info().equals(name)
                    && m.getDescriptor().getUtf8Info().equals(type.getMethodDescriptor())) {
                return m;
            }
        }
        throw new NoSuchMethodError();
    }

    public static MethodHandle getMethodHandleByConstantRefInfo(ConstantRefInfo reference) {
        ConstantNameAndTypeInfo nameAndType = reference.getNameAndType();
        String clazzName = reference.getClazz().getName().getUtf8Info();
        Clazz clazz = (Clazz) Heap.getObjectFromPool(MethodArea.CLAZZ_MAP.get(clazzName));
        if (Objects.isNull(clazz))
            clazz = AppClazzLoader.getInstance().loadClazz(clazzName);
        if (Objects.nonNull(clazz)) {
            // 将类的全限定名，方法名和描述符组合成key
            String key = clazzName + " " +
                    nameAndType.getName().getUtf8Info() + " " +
                    nameAndType.getDescriptor().getUtf8Info();
            // 如果之前解析过则直接返回
            MethodHandle methodHandle = METHOD_HANDLE_POOL.get(key);
            if (Objects.isNull(methodHandle)) {
                methodHandle = new MethodHandle();
                methodHandle.setMethodType(new MethodType(nameAndType.getDescriptor().getUtf8Info()));
                methodHandle.setMethodInfo(resolveMethod(clazz, nameAndType.getName().getUtf8Info(), methodHandle.getMethodType()));
                METHOD_HANDLE_POOL.put(key,methodHandle);
            }
            return methodHandle;
        }
        return null;
    }

}

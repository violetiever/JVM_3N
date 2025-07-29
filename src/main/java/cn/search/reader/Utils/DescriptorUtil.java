package cn.search.reader.Utils;

import cn.search.reader.Clazz.Clazz;
import cn.search.reader.ClazzLoader.AppClazzLoader;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.runtime.Heap;
import cn.search.runtime.MethodArea;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.search.reader.Enum.SpecialClazzType.*;

@Slf4j
public class DescriptorUtil {

    public static Clazz getReturnClazzByMethodDescriptor(String methodDescriptor, ClazzLoader loader) {
        // 判断loader是否为null，如果是null的话默认使用应用类加载器
        if (Objects.isNull(loader))
            loader = AppClazzLoader.getInstance();

        // 判断格式
        if (!methodDescriptor.startsWith("(") ||
                methodDescriptor.indexOf(')') < 0 ||
                methodDescriptor.indexOf('.') >= 0) {
            return null;
        }

        // 获取返回参数描述符
        String paramDescriptor = methodDescriptor.substring(methodDescriptor.indexOf(')') + 1);
        if (!paramDescriptor.isEmpty()) {
            return getClazzBySingleDescriptor(paramDescriptor, loader);
        }

        return null;
    }

    public static Clazz[] getMethodParameterClazzArrayByMethodDescriptor(String methodDescriptor, ClazzLoader loader) {
        // 判断loader是否为null，如果是null的话默认使用应用类加载器
        if (Objects.isNull(loader))
            loader = AppClazzLoader.getInstance();
        // 判断格式
        if (!methodDescriptor.startsWith("(") ||
                methodDescriptor.indexOf(')') < 0 ||
                methodDescriptor.indexOf('.') >= 0) {
            return null;
        }

        List<Clazz> result = new ArrayList<>();
        // 获取参数描述符
        String paramDescriptor = methodDescriptor.substring(1, methodDescriptor.indexOf(')'));
        int preIndex = 0;
        while (preIndex < paramDescriptor.length()) {
            int endIndex = getDescriptorEndIndex(paramDescriptor, preIndex);
            String descriptor = paramDescriptor.substring(preIndex, endIndex);
            result.add(getClazzBySingleDescriptor(descriptor, loader));
            preIndex = endIndex;
        }

        return result.toArray(new Clazz[0]);
    }

    // 获取当前描述符结束的索引
    public static int getDescriptorEndIndex(String paramDescriptor, int preIndex) {
        int endIndex = preIndex + 1;
        String descriptor = paramDescriptor.substring(preIndex, endIndex);
        if (Reference.getDescriptor().equals(descriptor)) {
            // 如果是引用类型则直接获取当前对象类型的结束符号 ';'
            endIndex = preIndex + paramDescriptor.substring(preIndex).indexOf(ReferenceEnd.getDescriptor()) + 1;
        } else if (Array.getDescriptor().equals(descriptor)) {
            return getDescriptorEndIndex(paramDescriptor, endIndex);
        }
        return endIndex;
    }

    // 根据描述符获取类的内部全限定名
    public static String getFullNameBySingleDescriptor(String descriptor) {
        // 先从特殊类型中获取类名
        String clazzName = DESCRIPTOR_MAP.get(descriptor);
        // 如果是引用类型则去除头尾的 'L' 和 ';'
        if (descriptor.startsWith(Reference.getDescriptor()))
            descriptor = descriptor.substring(1, descriptor.length() - 1);
        // 如果不是基本类型则描述符就是全限定名
        return Objects.nonNull(clazzName) ? clazzName : descriptor;
    }

    // 根据描述符获取类的clazz对象
    public static Clazz getClazzBySingleDescriptor(String singleDescriptor, ClazzLoader loader) {
        String fullName = getFullNameBySingleDescriptor(singleDescriptor);
        // 先从方法区中获取对象，不存在则尝试通过全限定名加载类
        Clazz clazz = (Clazz) Heap.getObjectFromPool(MethodArea.CLAZZ_MAP.get(fullName));
        if (Objects.isNull(clazz)) {
            // 如果是数组对象则直接调用 loadArrayClazz() 方法，不是则调用 loadClazz() 方法
            clazz = fullName.startsWith(Array.getDescriptor()) ? ClazzLoader.loadArrayClazz(fullName) : loader.loadClazz(fullName);
        }
        return clazz;
    }

    public static void main(String[] args) {
        String methodDescriptor = "(ILjava/lang/String;[[FZ)V";
        Clazz[] methodParameterClazzByDescriptor = getMethodParameterClazzArrayByMethodDescriptor(methodDescriptor, null);
        System.out.println(methodParameterClazzByDescriptor);
    }


}

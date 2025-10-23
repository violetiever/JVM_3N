package cn.search.intepreter.opt.Extended;

import cn.search.intepreter.opt.Opcode;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Enum.SpecialClazzType;
import cn.search.runtime.Frame;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

/**
 * Java虚拟机规范.Java SE 8版 309页
 */
public class multianewarray implements Opcode {


    static final HashMap<String, Function<Integer, Object>> T_MAP = new HashMap<>();

    static {
        T_MAP.put(SpecialClazzType.Boolean.getClazzName(), boolean[]::new);
        T_MAP.put(SpecialClazzType.Char.getClazzName(), char[]::new);
        T_MAP.put(SpecialClazzType.Float.getClazzName(), float[]::new);
        T_MAP.put(SpecialClazzType.Double.getClazzName(), double[]::new);
        T_MAP.put(SpecialClazzType.Byte.getClazzName(), byte[]::new);
        T_MAP.put(SpecialClazzType.Short.getClazzName(), short[]::new);
        T_MAP.put(SpecialClazzType.Int.getClazzName(), int[]::new);
        T_MAP.put(SpecialClazzType.Long.getClazzName(), long[]::new);
    }

    @Override
    public void opt(Frame frame) {
        int index = ((frame.getNextCode() << 8) | (frame.getNextCode())) - 1;
        ConstantClassInfo constantClassInfo = (ConstantClassInfo) frame.getRuntimeConstantPool()[index];
        constantClassInfo.resolve();
        int dimensions = frame.getNextCode();
        int[] count = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            int dim = (int) frame.getOperandStack().pop();
            if (dim < 0)
                throw new NegativeArraySizeException();
            count[i] = dim;
        }
        Object multiArray = createMultiDimensionalArray(constantClassInfo.getClazzInfo(), count, 0);
        frame.getOperandStack().push(multiArray);
        frame.getNextCode();
    }

    private Object createMultiDimensionalArray(Clazz clazz, int[] count, int currentDimension) {
        int size = count[currentDimension];
        Clazz componentType = clazz.getComponentType();
        if (!componentType.isArray()) {
            // 最后一维，创建组件类型的数组
            Object currentArray = null;
            Function<Integer, Object> function = T_MAP.get(componentType.getName());
            if (Objects.isNull(function)) {
                try {
                    currentArray = Array.newInstance(Class.forName(componentType.getName()), size);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                currentArray = function.apply(size);
            }
            return currentArray;
        } else {
            // 不是最后一维，直接创建Object数组
            Object[] currentArray = new Object[size];
            int subDimension = ++currentDimension;
            // 递归创建子数组
            for (int i = 0; i < size; i++) {
                Object subArray = createMultiDimensionalArray(componentType, count, subDimension);
                currentArray[i] = subArray;
            }
            return currentArray;
        }
    }

}

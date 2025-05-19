package cn.search.reader.Utils;

import java.lang.reflect.Field;

public class CopyFieldUtil {



    public static void copySameNameFields(Object target, Object source) {
        if (target == null || source == null) return;

        Class<?> targetClass = target.getClass();
        Class<?> sourceClass = source.getClass();

        for (Field sourceField : sourceClass.getFields()) {
            try {

                Field targetField = targetClass.getField(sourceField.getName());

                sourceField.setAccessible(true);
                targetField.setAccessible(true);

                Object sourceValue = sourceField.get(source);

                if (sourceValue != null) {
                    targetField.set(target, sourceValue);
                }
            } catch (IllegalAccessException e) {
                // 处理访问异常
            } catch (NoSuchFieldException ignored) {
                //
            }
        }
    }


}

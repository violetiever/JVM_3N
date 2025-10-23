package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.reader.ClazzLoader.ClazzLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantFieldRefInfo extends ConstantRefInfo {

    private FieldInfo fieldInfo;

    public ConstantFieldRefInfo(DataInputStream dataInput) {
        super(dataInput);
    }

    @Override
    public void resolve() {
        if (Objects.nonNull(this.fieldInfo))
            return;

        String clazzName = this.getClazz().getName().getUtf8Info();
        String fieldName = this.getNameAndType().getName().getUtf8Info();
        String descriptor = this.getNameAndType().getDescriptor().getUtf8Info();

        // 查找当前类
        Clazz clazz = ClazzLoader.loadClazzByLoader(clazzName, this.getThisClazz().getClazzLoader());

        // 此处JVM规范描述有歧义
        while (Objects.nonNull(clazz)) {
            this.fieldInfo = clazz.getFieldByNameAndDescriptor(fieldName, descriptor);
            if (Objects.nonNull(this.fieldInfo))
                return;
            // 查找类实现的接口
            for (ConstantClassInfo interfaceClass : clazz.getInterfacesInfo()) {
                Clazz interfaceClazz = ClazzLoader.loadClazzByLoader(interfaceClass.getName().getUtf8Info(), this.getThisClazz().getClazzLoader());
                this.fieldInfo = interfaceClazz.getFieldByNameAndDescriptor(fieldName, descriptor);
                if (Objects.nonNull(this.fieldInfo))
                    return;
            }
            String superClazzName = clazz.getSuperClassInfo().getName().getUtf8Info();
            // 获取当前类的父类循环寻找字段
            clazz = ClazzLoader.loadClazzByLoader(superClazzName, this.getThisClazz().getClazzLoader());
        }
    }

}

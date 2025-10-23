package cn.search.reader.Clazz.FieldInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.AttributeInfo.AttributeInfo;
import cn.search.reader.Clazz.AttributeInfo.ConstantValueAttribute;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Enum.SpecialClazzType;
import cn.search.reader.Usinged.U2;
import cn.search.reader.Utils.DescriptorUtil;
import lombok.Data;

import java.io.DataInputStream;
import java.util.Arrays;

@Data
@ClazzConstructor
public class FieldInfo {

    // u2
    @ClazzField(order = 0)
    private U2 accessFlags;

    // u2
    @ClazzField(order = 1)
    private U2 nameIndex;

    // u2
    @ClazzField(order = 2)
    private U2 descriptorIndex;

    // u2
    @ClazzField(order = 3)
    private U2 attributesCount;

    // length = attributesCount
    private AttributeInfo[] attributes;

    private String[] accessFlag;

    private ConstantUtf8Info name;

    private ConstantUtf8Info descriptor;

    private Clazz thisClazz;

    // 字段的值
    private Object fieldValue = null;

    public FieldInfo() {

    }

    public FieldInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool, Clazz thisClazz) {
        this.accessFlag = Clazz.resolveAccessFlag(this.accessFlags);
        this.name = (ConstantUtf8Info) constantPool[this.nameIndex.getValue() - 1];
        this.descriptor = (ConstantUtf8Info) constantPool[this.descriptorIndex.getValue() - 1];
        this.attributes = new AttributeInfo[this.attributesCount.getValue()];
        for (int i = 0; i < this.attributes.length; i++) {
            attributes[i] = AttributeInfo.getAttributeInfoByNameIndex(dataInput, constantPool);
        }
        this.thisClazz = thisClazz;
    }

    public void prepare() {
        String descriptor = this.descriptor.getUtf8Info();
        Clazz fieldClazz = DescriptorUtil.getClazzBySingleDescriptor(descriptor, thisClazz.getClazzLoader());
        boolean staticFlag = false;
        boolean constantValueFlag = false;
        ConstantValueAttribute constantValueAttribute = null;
        for (String access : this.accessFlag)
            if (access.equals(Clazz.ACCESS_FLAGS_MAP.get(0x0008)))
                staticFlag = true;

        for (AttributeInfo attribute : this.attributes)
            if (attribute instanceof ConstantValueAttribute) {
                constantValueAttribute = (ConstantValueAttribute) attribute;
                constantValueFlag = true;
            }

        // 是原始类型的话获取默认值
        if (fieldClazz.isPrimitive())
            this.fieldValue = SpecialClazzType.DEFAULT_MAP.get(descriptor);

        // 如果是静态变量并且有ConstantValue属性则直接赋值
        if (staticFlag && constantValueFlag)
            this.fieldValue = constantValueAttribute.getConstantValue();
    }

    // todo 新增字段解析逻辑
    public void resolve() {

    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "attributes=" + Arrays.toString(attributes) +
                ", accessFlag=" + Arrays.toString(accessFlag) +
                ", name=" + name +
                ", descriptor=" + descriptor +
                ", fieldValue=" + fieldValue +
                '}';
    }

}


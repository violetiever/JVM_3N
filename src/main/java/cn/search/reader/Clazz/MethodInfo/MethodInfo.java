package cn.search.reader.Clazz.MethodInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.AttributeInfo.AttributeInfo;
import cn.search.reader.Clazz.AttributeInfo.CodeAttribute;
import cn.search.reader.Clazz.AttributeInfo.ExceptionsAttribute;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U2;
import cn.search.reader.Utils.CommonUtil;
import lombok.Data;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
@ClazzConstructor
public class MethodInfo {

    public static HashMap<Integer, String> METHOD_ACCESS_FLAGS_MAP = new HashMap<>();

    static {
        METHOD_ACCESS_FLAGS_MAP.put(0x0001, "ACC_PUBLIC");
        METHOD_ACCESS_FLAGS_MAP.put(0x0002, "ACC_PRIVATE");
        METHOD_ACCESS_FLAGS_MAP.put(0x0004, "ACC_PROTECTED");
        METHOD_ACCESS_FLAGS_MAP.put(0x0008, "ACC_STATIC");
        METHOD_ACCESS_FLAGS_MAP.put(0x0010, "ACC_FINAL");
        METHOD_ACCESS_FLAGS_MAP.put(0x0020, "ACC_SYNCHRONIZED");
        METHOD_ACCESS_FLAGS_MAP.put(0x0040, "ACC_BRIDGE");
        METHOD_ACCESS_FLAGS_MAP.put(0x0080, "ACC_VARARGS");
        METHOD_ACCESS_FLAGS_MAP.put(0x0100, "ACC_NATIVE");
        METHOD_ACCESS_FLAGS_MAP.put(0x0400, "ACC_ABSTRACT");
        METHOD_ACCESS_FLAGS_MAP.put(0x0800, "ACC_STRICT");
        METHOD_ACCESS_FLAGS_MAP.put(0x1000, "ACC_SYNTHETIC");
    }


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

    // 指向解析时所属的class文件
    private Clazz thisClazz;

    public MethodInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool, Clazz thisClazz) {
        this.attributes = new AttributeInfo[this.attributesCount.getValue()];
        for (int i = 0; i < this.attributes.length; i++)
            attributes[i] = AttributeInfo.getAttributeInfoByNameIndex(dataInput, constantPool);
        this.accessFlag = CommonUtil.resolveAccessFlag(METHOD_ACCESS_FLAGS_MAP,this.accessFlags);
        this.name = (ConstantUtf8Info) constantPool[this.nameIndex.getValue() - 1];
        this.descriptor = (ConstantUtf8Info) constantPool[this.descriptorIndex.getValue() - 1];
        this.thisClazz = thisClazz;
    }

    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attribute : attributes)
            if (attribute instanceof CodeAttribute)
                return ((CodeAttribute) attribute);
        return null;
    }

    public ExceptionsAttribute getExceptionsAttribute() {
        for (AttributeInfo attribute : attributes)
            if (attribute instanceof ExceptionsAttribute)
                return ((ExceptionsAttribute) attribute);
        return null;
    }

    @Override
    public String toString() {
        return "MethodInfo{" + "\n" +
                " name=" + name + "\n" +
                ",attributes=" + Arrays.toString(attributes) + "\n" +
                ", accessFlag=" + Arrays.toString(accessFlag) + "\n" +
                ", descriptor=" + descriptor + "\n" +
                '}';
    }

}

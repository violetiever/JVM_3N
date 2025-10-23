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
import lombok.Data;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
@ClazzConstructor
public class MethodInfo {

    public static HashMap<Integer, String> ACCESS_FLAGS_MAP = new HashMap<>();

    static {
        ACCESS_FLAGS_MAP.put(0x0001, "ACC_PUBLIC");
        ACCESS_FLAGS_MAP.put(0x0002, "ACC_PRIVATE");
        ACCESS_FLAGS_MAP.put(0x0004, "ACC_PROTECTED");
        ACCESS_FLAGS_MAP.put(0x0008, "ACC_STATIC");
        ACCESS_FLAGS_MAP.put(0x0010, "ACC_FINAL");
        ACCESS_FLAGS_MAP.put(0x0020, "ACC_SYNCHRONIZED");
        ACCESS_FLAGS_MAP.put(0x0040, "ACC_BRIDGE");
        ACCESS_FLAGS_MAP.put(0x0080, "ACC_VARARGS");
        ACCESS_FLAGS_MAP.put(0x0100, "ACC_NATIVE");
        ACCESS_FLAGS_MAP.put(0x0400, "ACC_ABSTRACT");
        ACCESS_FLAGS_MAP.put(0x0800, "ACC_STRICT");
        ACCESS_FLAGS_MAP.put(0x1000, "ACC_SYNTHETIC");
    }

    public static String[] resolveAccessFlag(U2 accessFlags) {
        List<String> accessFlagsTemp = new ArrayList<>();
        ACCESS_FLAGS_MAP.keySet().forEach(k -> {
            if ((accessFlags.getValue() & k) == k)
                accessFlagsTemp.add(ACCESS_FLAGS_MAP.get(k));
        });
        String[] result = new String[accessFlagsTemp.size()];
        for (int i = 0; i < accessFlagsTemp.size(); i++) {
            result[i] = accessFlagsTemp.get(i);
        }
        return result;
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
        this.accessFlag = MethodInfo.resolveAccessFlag(this.accessFlags);
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

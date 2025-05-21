package cn.search.reader.Clazz;


import cn.search.reader.Clazz.AttributeInfo.AttributeInfo;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.Usinged.U2;
import cn.search.reader.Usinged.U4;
import lombok.AllArgsConstructor;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class Clazz {

    public static HashMap<Integer, String> ACCESS_FLAGS_MAP = new HashMap<>();

    static {
        ACCESS_FLAGS_MAP.put(0x0001, "ACC_PUBLIC");
        ACCESS_FLAGS_MAP.put(0x0002, "ACC_PRIVATE");
        ACCESS_FLAGS_MAP.put(0x0004, "ACC_PROTECTED");
        ACCESS_FLAGS_MAP.put(0x0008, "ACC_STATIC");
        ACCESS_FLAGS_MAP.put(0x0010, "ACC_FINAL");
        ACCESS_FLAGS_MAP.put(0x0020, "ACC_SUPER");
        ACCESS_FLAGS_MAP.put(0x0200, "ACC_INTERFACE");
        ACCESS_FLAGS_MAP.put(0x0400, "ACC_ABSTRACT");
        ACCESS_FLAGS_MAP.put(0x1000, "ACC_SYNTHETIC");
        ACCESS_FLAGS_MAP.put(0x2000, "ACC_ANNOTATION");
        ACCESS_FLAGS_MAP.put(0x4000, "ACC_ENUM");
        ACCESS_FLAGS_MAP.put(0x8000, "ACC_MANDATED");
    }

    public static String[] resolveAccessFlag(U2 accessFlags) {

        List<String> accessFlagsTemp = new ArrayList<>();

        ACCESS_FLAGS_MAP.keySet().forEach(k -> {
            if ((accessFlags.getValue() & k) == k) {
                accessFlagsTemp.add(ACCESS_FLAGS_MAP.get(k));
            }
        });

        String[] result = new String[accessFlagsTemp.size()];
        for (int i = 0; i < accessFlagsTemp.size(); i++) {
            result[i] = accessFlagsTemp.get(i);
        }
        return result;
    }

    //protected byte Header[] = {202,254,186,190};

    // u4
    private U4 magic;

    // u2
    private U2 minorVersion;

    // u2
    private U2 majorVersion;

    // u2
    private U2 constantPoolCount;

    // length = constantPoolCount - 1
    private ConstantCpInfo[] constantPool;

    // u2
    private U2 accessFlags;

    private String[] accessFlagsName;

    // u2
    private U2 thisClass;

    private ConstantClassInfo thisClassInfo;

    // u2
    private U2 superClass;

    private ConstantClassInfo superClassInfo;

    // u2
    private U2 interfacesCount;

    // length = interfacesCount
    private U2[] interfaces;

    private ConstantClassInfo[] interfacesInfo;

    // u2
    private U2 fieldsCount;

    // length = fieldsCount
    private FieldInfo[] fields;

    // u2
    private U2 methodsCount;

    // length = methodsCount
    private MethodInfo[] methods;

    // u2
    private U2 attributeCount;

    // length = attributeCount
    private AttributeInfo[] attributes;

    public Clazz(DataInputStream dataInput) throws Exception {
        this.magic = new U4(dataInput);
        this.minorVersion = new U2(dataInput);
        this.majorVersion = new U2(dataInput);

        this.constantPoolCount = new U2(dataInput);
        int constantPoolLen = this.constantPoolCount.getValue() - 1;
        this.constantPool = new ConstantCpInfo[constantPoolLen];
        for (int i = 0; i < constantPoolLen; i++) {
            this.constantPool[i] = ConstantCpInfo.getCpInfoByTag(dataInput, constantPool);
        }
        for (int i = 0; i < constantPoolLen; i++) {
            this.constantPool[i].initConstant(constantPool);
            this.constantPool[i].link();
        }

        this.accessFlags = new U2(dataInput);
        this.accessFlagsName = Clazz.resolveAccessFlag(this.accessFlags);

        this.thisClass = new U2(dataInput);
        this.thisClassInfo = (ConstantClassInfo) this.constantPool[this.thisClass.getValue() - 1];

        this.superClass = new U2(dataInput);
        if (this.superClass.getValue() != 0) {
            this.superClassInfo = (ConstantClassInfo) this.constantPool[this.superClass.getValue() - 1];
        }

        this.interfacesCount = new U2(dataInput);
        this.interfaces = new U2[interfacesCount.getValue()];
        this.interfacesInfo = new ConstantClassInfo[interfacesCount.getValue()];
        for (int i = 0; i < this.interfaces.length; i++) {
            this.interfaces[i] = new U2(dataInput);
            this.interfacesInfo[i] = (ConstantClassInfo) this.constantPool[this.interfaces[i].getValue() - 1];
        }

        this.fieldsCount = new U2(dataInput);
        this.fields = new FieldInfo[this.fieldsCount.getValue()];
        for (int i = 0; i < this.fields.length; i++) {
            this.fields[i] = new FieldInfo(dataInput, this.constantPool);
        }

        this.methodsCount = new U2(dataInput);
        this.methods = new MethodInfo[this.methodsCount.getValue()];
        for (int i = 0; i < this.methods.length; i++) {
            this.methods[i] = new MethodInfo(dataInput, this.constantPool);
        }

        this.attributeCount = new U2(dataInput);
        this.attributes = new AttributeInfo[this.attributeCount.getValue()];
        for (int i = 0; i < this.attributes.length; i++) {
            attributes[i] = AttributeInfo.getAttributeInfoByNameIndex(dataInput, this.constantPool);
        }


        System.out.println("test");
    }

    public U4 getMagic() {
        return this.magic;
    }

    public U2 getMinorVersion() {
        return this.minorVersion;
    }

    public U2 getMajorVersion() {
        return this.majorVersion;
    }

    public U2 getConstantPoolCount() {
        return this.constantPoolCount;
    }

    public ConstantCpInfo[] getConstantPool() {
        return this.constantPool;
    }

    public U2 getAccessFlags() {
        return this.accessFlags;
    }

    public U2 getThisClass() {
        return this.thisClass;
    }

    public U2 getSuperClass() {
        return this.superClass;
    }

    public U2 getInterfacesCount() {
        return this.interfacesCount;
    }

    public U2[] getInterfaces() {
        return this.interfaces;
    }

    public U2 getFieldsCount() {
        return this.fieldsCount;
    }

    public FieldInfo[] getFields() {
        return this.fields;
    }

    public U2 getMethodsCount() {
        return this.methodsCount;
    }

    public MethodInfo[] getMethods() {
        return this.methods;
    }

    public U2 getAttributeCount() {
        return this.attributeCount;
    }

    public AttributeInfo[] getAttributes() {
        return this.attributes;
    }

    public void setMagic(U4 magic) {
        this.magic = magic;
    }

    public void setMinorVersion(U2 minorVersion) {
        this.minorVersion = minorVersion;
    }

    public void setMajorVersion(U2 majorVersion) {
        this.majorVersion = majorVersion;
    }

    public void setConstantPoolCount(U2 constantPoolCount) {
        this.constantPoolCount = constantPoolCount;
    }

    public void setConstantPool(ConstantCpInfo[] constantPool) {
        this.constantPool = constantPool;
    }

    public void setAccessFlags(U2 accessFlags) {
        this.accessFlags = accessFlags;
    }

    public void setThisClass(U2 thisClass) {
        this.thisClass = thisClass;
    }

    public void setSuperClass(U2 superClass) {
        this.superClass = superClass;
    }

    public void setInterfacesCount(U2 interfacesCount) {
        this.interfacesCount = interfacesCount;
    }

    public void setInterfaces(U2[] interfaces) {
        this.interfaces = interfaces;
    }

    public void setFieldsCount(U2 fieldsCount) {
        this.fieldsCount = fieldsCount;
    }

    public void setFields(FieldInfo[] fields) {
        this.fields = fields;
    }

    public void setMethodsCount(U2 methodsCount) {
        this.methodsCount = methodsCount;
    }

    public void setMethods(MethodInfo[] methods) {
        this.methods = methods;
    }

    public void setAttributeCount(U2 attributeCount) {
        this.attributeCount = attributeCount;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Clazz)) return false;
        final Clazz other = (Clazz) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$magic = this.getMagic();
        final Object other$magic = other.getMagic();
        if (this$magic == null ? other$magic != null : !this$magic.equals(other$magic)) return false;
        final Object this$minorVersion = this.getMinorVersion();
        final Object other$minorVersion = other.getMinorVersion();
        if (this$minorVersion == null ? other$minorVersion != null : !this$minorVersion.equals(other$minorVersion))
            return false;
        final Object this$majorVersion = this.getMajorVersion();
        final Object other$majorVersion = other.getMajorVersion();
        if (this$majorVersion == null ? other$majorVersion != null : !this$majorVersion.equals(other$majorVersion))
            return false;
        final Object this$constantPoolCount = this.getConstantPoolCount();
        final Object other$constantPoolCount = other.getConstantPoolCount();
        if (this$constantPoolCount == null ? other$constantPoolCount != null : !this$constantPoolCount.equals(other$constantPoolCount))
            return false;
        if (!java.util.Arrays.deepEquals(this.getConstantPool(), other.getConstantPool())) return false;
        final Object this$accessFlags = this.getAccessFlags();
        final Object other$accessFlags = other.getAccessFlags();
        if (this$accessFlags == null ? other$accessFlags != null : !this$accessFlags.equals(other$accessFlags))
            return false;
        final Object this$thisClass = this.getThisClass();
        final Object other$thisClass = other.getThisClass();
        if (this$thisClass == null ? other$thisClass != null : !this$thisClass.equals(other$thisClass)) return false;
        final Object this$superClass = this.getSuperClass();
        final Object other$superClass = other.getSuperClass();
        if (this$superClass == null ? other$superClass != null : !this$superClass.equals(other$superClass))
            return false;
        final Object this$interfacesCount = this.getInterfacesCount();
        final Object other$interfacesCount = other.getInterfacesCount();
        if (this$interfacesCount == null ? other$interfacesCount != null : !this$interfacesCount.equals(other$interfacesCount))
            return false;
        if (!java.util.Arrays.deepEquals(this.getInterfaces(), other.getInterfaces())) return false;
        final Object this$fieldsCount = this.getFieldsCount();
        final Object other$fieldsCount = other.getFieldsCount();
        if (this$fieldsCount == null ? other$fieldsCount != null : !this$fieldsCount.equals(other$fieldsCount))
            return false;
        if (!java.util.Arrays.deepEquals(this.getFields(), other.getFields())) return false;
        final Object this$methodsCount = this.getMethodsCount();
        final Object other$methodsCount = other.getMethodsCount();
        if (this$methodsCount == null ? other$methodsCount != null : !this$methodsCount.equals(other$methodsCount))
            return false;
        if (!java.util.Arrays.deepEquals(this.getMethods(), other.getMethods())) return false;
        final Object this$attributeCount = this.getAttributeCount();
        final Object other$attributeCount = other.getAttributeCount();
        if (this$attributeCount == null ? other$attributeCount != null : !this$attributeCount.equals(other$attributeCount))
            return false;
        if (!java.util.Arrays.deepEquals(this.getAttributes(), other.getAttributes())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Clazz;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $magic = this.getMagic();
        result = result * PRIME + ($magic == null ? 43 : $magic.hashCode());
        final Object $minorVersion = this.getMinorVersion();
        result = result * PRIME + ($minorVersion == null ? 43 : $minorVersion.hashCode());
        final Object $majorVersion = this.getMajorVersion();
        result = result * PRIME + ($majorVersion == null ? 43 : $majorVersion.hashCode());
        final Object $constantPoolCount = this.getConstantPoolCount();
        result = result * PRIME + ($constantPoolCount == null ? 43 : $constantPoolCount.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getConstantPool());
        final Object $accessFlags = this.getAccessFlags();
        result = result * PRIME + ($accessFlags == null ? 43 : $accessFlags.hashCode());
        final Object $thisClass = this.getThisClass();
        result = result * PRIME + ($thisClass == null ? 43 : $thisClass.hashCode());
        final Object $superClass = this.getSuperClass();
        result = result * PRIME + ($superClass == null ? 43 : $superClass.hashCode());
        final Object $interfacesCount = this.getInterfacesCount();
        result = result * PRIME + ($interfacesCount == null ? 43 : $interfacesCount.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getInterfaces());
        final Object $fieldsCount = this.getFieldsCount();
        result = result * PRIME + ($fieldsCount == null ? 43 : $fieldsCount.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getFields());
        final Object $methodsCount = this.getMethodsCount();
        result = result * PRIME + ($methodsCount == null ? 43 : $methodsCount.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getMethods());
        final Object $attributeCount = this.getAttributeCount();
        result = result * PRIME + ($attributeCount == null ? 43 : $attributeCount.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getAttributes());
        return result;
    }

    public String toString() {
        return "Clazz(magic=" + this.getMagic() + ", minorVersion=" + this.getMinorVersion() + ", majorVersion=" + this.getMajorVersion() + ", constantPoolCount=" + this.getConstantPoolCount() + ", constantPool=" + java.util.Arrays.deepToString(this.getConstantPool()) + ", accessFlags=" + this.getAccessFlags() + ", thisClass=" + this.getThisClass() + ", superClass=" + this.getSuperClass() + ", interfacesCount=" + this.getInterfacesCount() + ", interfaces=" + java.util.Arrays.deepToString(this.getInterfaces()) + ", fieldsCount=" + this.getFieldsCount() + ", fields=" + java.util.Arrays.deepToString(this.getFields()) + ", methodsCount=" + this.getMethodsCount() + ", methods=" + java.util.Arrays.deepToString(this.getMethods()) + ", attributeCount=" + this.getAttributeCount() + ", attributes=" + java.util.Arrays.deepToString(this.getAttributes()) + ")";
    }


}






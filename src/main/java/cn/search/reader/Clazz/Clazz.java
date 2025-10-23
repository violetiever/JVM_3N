package cn.search.reader.Clazz;


import cn.search.reader.Clazz.AttributeInfo.AttributeInfo;
import cn.search.reader.Clazz.AttributeInfo.CodeAttribute;
import cn.search.reader.Clazz.CpInfo.*;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import cn.search.reader.Clazz.MethodInfo.MethodInfo;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.reader.Enum.SpecialClazzType;
import cn.search.reader.Usinged.U2;
import cn.search.reader.Usinged.U4;
import cn.search.reader.Utils.DescriptorUtil;
import cn.search.runtime.Frame;
import lombok.AllArgsConstructor;

import java.io.DataInputStream;
import java.util.*;

import static cn.search.reader.Enum.SpecialClazzType.Array;

@AllArgsConstructor
public class Clazz {

    public static HashMap<Integer, String> ACCESS_FLAGS_MAP = new HashMap<>();

    static {
        ACCESS_FLAGS_MAP.put(0x0001, "ACC_PUBLIC");
        ACCESS_FLAGS_MAP.put(0x0010, "ACC_FINAL");
        ACCESS_FLAGS_MAP.put(0x0020, "ACC_SUPER");
        ACCESS_FLAGS_MAP.put(0x0200, "ACC_INTERFACE");
        ACCESS_FLAGS_MAP.put(0x0400, "ACC_ABSTRACT");
        ACCESS_FLAGS_MAP.put(0x1000, "ACC_SYNTHETIC");
        ACCESS_FLAGS_MAP.put(0x2000, "ACC_ANNOTATION");
        ACCESS_FLAGS_MAP.put(0x4000, "ACC_ENUM");
    }


    // 生成特殊的clazz对象，例如原始数据类型
    public Clazz(String name) {
        this.thisClassInfo = new ConstantClassInfo();
        ConstantUtf8Info utf8Name = new ConstantUtf8Info();
        utf8Name.setUtf8Info(name);
        this.thisClassInfo.setName(utf8Name);
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

    private ClazzLoader clazzLoader;

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

    private boolean isInitialized = false;

    public Clazz(DataInputStream dataInput) throws Exception {
        this.magic = new U4(dataInput);
        this.minorVersion = new U2(dataInput);
        this.majorVersion = new U2(dataInput);

        this.constantPoolCount = new U2(dataInput);
        int constantPoolLen = this.constantPoolCount.getValue() - 1;
        this.constantPool = new ConstantCpInfo[constantPoolLen];
        for (int i = 0; i < constantPoolLen; i++) {
            this.constantPool[i] = ConstantCpInfo.getCpInfoByTag(dataInput, constantPool, this);
            // 如果是8字节常量则占用两个表元素空间
            if (this.constantPool[i] instanceof ConstantLongInfo || this.constantPool[i] instanceof ConstantDoubleInfo) {
                i++;
            }
        }
        for (int i = 0; i < constantPoolLen; i++) {
            // 判空，防止8字节常量占用两个表元素空间的情况
            if (Objects.nonNull(this.constantPool[i])) {
                this.constantPool[i].initConstant(constantPool);
//                this.constantPool[i].link();
            }
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
            this.fields[i] = new FieldInfo(dataInput, this.constantPool, this);
        }

        this.methodsCount = new U2(dataInput);
        this.methods = new MethodInfo[this.methodsCount.getValue()];
        for (int i = 0; i < this.methods.length; i++) {
            this.methods[i] = new MethodInfo(dataInput, this.constantPool, this);
        }

        this.attributeCount = new U2(dataInput);
        this.attributes = new AttributeInfo[this.attributeCount.getValue()];
        for (int i = 0; i < this.attributes.length; i++) {
            attributes[i] = AttributeInfo.getAttributeInfoByNameIndex(dataInput, this.constantPool);
        }

    }

    // 将内部全限定名转换为全限定名返回
    public String getName() {
        return this.thisClassInfo.getName().getUtf8Info().replace("/", ".");
    }

    // 获取组件类型的clazz对象
    public Clazz getComponentType() {
        if (isArray()) {
            return DescriptorUtil.getClazzBySingleDescriptor(this.thisClassInfo.getName().getUtf8Info().substring(1), this.getClazzLoader());
        }
        return null;
    }

    public boolean isArray() {
        return this.getName().startsWith(Array.getDescriptor());
    }

    public boolean isPrimitive() {
        return SpecialClazzType.DESCRIPTOR_MAP.containsValue(this.getName());
    }

    public boolean isInterface() {
        return Arrays.asList(this.getAccessFlagsName()).contains(ACCESS_FLAGS_MAP.get(0x0200));
    }

    public boolean inInstanceOf(Clazz clazzInfo) {
        if (this.isInterface())
            return this.isImplOf(clazzInfo);
        else if (this.isArray())
            return this.isSonArrayOf(clazzInfo);
        else
            return this.isSonClazzOf(clazzInfo);
    }

    // 是clazz的子类
    public boolean isSonClazzOf(Clazz clazz) {
        Clazz superClazz = this;
        while (Objects.nonNull(superClazz)) {
            if (superClazz.equals(clazz))
                return true;
            superClazz.getSuperClassInfo().resolve();
            superClazz = superClazz.getSuperClassInfo().getClazzInfo();
        }
        return false;
    }

    // 是clazz的实现类
    public boolean isImplOf(Clazz clazz) {
        Clazz superClazz = this;
        while (Objects.nonNull(superClazz)) {
            if (superClazz.equals(clazz)) return true;
            for (ConstantClassInfo interfaceClass : superClazz.interfacesInfo) {
                interfaceClass.resolve();
                if (interfaceClass.getClazzInfo().equals(clazz))
                    return true;
            }
            superClazz.getSuperClassInfo().resolve();
            superClazz = superClazz.getSuperClassInfo().getClazzInfo();
        }
        return false;
    }

    // 是clazz的子类数组
    public boolean isSonArrayOf(Clazz clazz) {
        if (clazz.isInterface())
            return this.isImplOf(clazz);
        else if (clazz.isArray()) {
            Clazz TC = clazz.getComponentType();
            Clazz SC = this.getComponentType();
            return SC.isSonArrayOf(TC);
        } else
            return clazz.equals(ClazzLoader.loadClazzByLoader("java.lang.Object", this.getClazzLoader()));
    }

    // 转换成class
    public Class transToClass() {
        try {
            return ClassLoader.getSystemClassLoader().loadClass(this.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 准备阶段
    public void prepare() {
        if (Objects.nonNull(constantPool))
            for (ConstantCpInfo constantCpInfo : constantPool) {
                if (Objects.nonNull(constantCpInfo))
                    constantCpInfo.prepare();
            }
        if (Objects.nonNull(fields))
            for (FieldInfo field : fields) {
                if (Objects.nonNull(field))
                    field.prepare();
            }
    }

    // 解析阶段
    public void resolve() {
        if (Objects.nonNull(constantPool))
            for (ConstantCpInfo constantCpInfo : constantPool) {
                if (Objects.nonNull(constantCpInfo))
                    constantCpInfo.resolve();
            }
        if (Objects.nonNull(fields))
            for (FieldInfo field : fields) {
                if (Objects.nonNull(field))
                    field.resolve();
            }
    }

    // 执行类的初始化函数
    public void initialize(Frame frame) {
        synchronized (this) {
            if (!this.isInitialized()) {
                this.setInitialized(true);
                MethodInfo method = this.getMethodByNameAndDescriptor("<clinit>", "()V");
                CodeAttribute codeAttribute = method.getCodeAttribute();
                Frame newFrame = new Frame(frame,
                        new Long[codeAttribute.getMaxLocals().getValue()],
                        new Stack<>(),
                        this.getConstantPool(),
                        method,
                        codeAttribute.getCode(),
                        this);
                newFrame.execute();
                this.setInitialized(true);
            }
        }
    }

    public FieldInfo getFieldByNameAndDescriptor(String fieldName, String descriptor) {
        for (FieldInfo field : this.getFields())
            if (fieldName.equals(field.getName().getUtf8Info()) && descriptor.equals(field.getDescriptor().getUtf8Info()))
                return field;
        return null;
    }

    public MethodInfo getMethodByNameAndDescriptor(String methodName, String descriptor) {
        if (Objects.nonNull(this.getMethods()))
            for (MethodInfo method : this.getMethods())
                if (methodName.equals(method.getName().getUtf8Info()) && descriptor.equals(method.getDescriptor().getUtf8Info()))
                    return method;
        return null;
    }

    public MethodInfo[] getMethodByName(String methodName) {
        List<MethodInfo> methodInfoList = new ArrayList<>();
        for (MethodInfo method : this.getMethods())
            if (methodName.equals(method.getName().getUtf8Info()))
                methodInfoList.add(method);
        return methodInfoList.toArray(methodInfoList.toArray(new MethodInfo[0]));
    }

    public AttributeInfo getAttributeByClass(Class clazz) {
        for (AttributeInfo attribute : this.getAttributes())
            if (attribute.getClass() == clazz)
                return attribute;
        return null;
    }


    public ConstantClassInfo getSuperClassInfo() {
        return superClassInfo;
    }

    public void setSuperClassInfo(ConstantClassInfo superClassInfo) {
        this.superClassInfo = superClassInfo;
    }

    public ConstantClassInfo[] getInterfacesInfo() {
        return interfacesInfo;
    }

    public void setInterfacesInfo(ConstantClassInfo[] interfacesInfo) {
        this.interfacesInfo = interfacesInfo;
    }

    public String[] getAccessFlagsName() {
        return accessFlagsName;
    }

    public void setAccessFlagsName(String[] accessFlagsName) {
        this.accessFlagsName = accessFlagsName;
    }

    public ClazzLoader getClazzLoader() {
        return clazzLoader;
    }

    public void setClazzLoader(ClazzLoader clazzLoader) {
        this.clazzLoader = clazzLoader;
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

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clazz clazz = (Clazz) o;
        return Objects.equals(clazzLoader, clazz.clazzLoader) && Objects.equals(magic, clazz.magic) && Objects.equals(minorVersion, clazz.minorVersion) && Objects.equals(majorVersion, clazz.majorVersion) && Objects.equals(constantPoolCount, clazz.constantPoolCount) && Arrays.equals(constantPool, clazz.constantPool) && Objects.equals(accessFlags, clazz.accessFlags) && Arrays.equals(accessFlagsName, clazz.accessFlagsName) && Objects.equals(thisClass, clazz.thisClass) && Objects.equals(thisClassInfo, clazz.thisClassInfo) && Objects.equals(superClass, clazz.superClass) && Objects.equals(superClassInfo, clazz.superClassInfo) && Objects.equals(interfacesCount, clazz.interfacesCount) && Arrays.equals(interfaces, clazz.interfaces) && Arrays.equals(interfacesInfo, clazz.interfacesInfo) && Objects.equals(fieldsCount, clazz.fieldsCount) && Arrays.equals(fields, clazz.fields) && Objects.equals(methodsCount, clazz.methodsCount) && Arrays.equals(methods, clazz.methods) && Objects.equals(attributeCount, clazz.attributeCount) && Arrays.equals(attributes, clazz.attributes);
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "name= " + this.getName() +
                '}';
    }

    public String getDetail() {
        return "Clazz{" +
                "name= " + this.getName() +
                ", clazzLoader=" + (Objects.isNull(clazzLoader) ? "null" : clazzLoader.getClass().getName()) +
                ", magic=" + (Objects.isNull(magic) ? "null" : magic.getValue()) +
                ", minorVersion=" + (Objects.isNull(minorVersion) ? "null" : minorVersion.getValue()) +
                ", majorVersion=" + (Objects.isNull(majorVersion) ? "null" : majorVersion.getValue()) +
                ", constantPool=" + Arrays.toString(constantPool) +
                ", accessFlagsName=" + Arrays.toString(accessFlagsName) +
                ", interfacesInfo=" + Arrays.toString(interfacesInfo) +
                ", fields=" + Arrays.toString(fields) +
                ", methods=" + Arrays.toString(methods) +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }


}






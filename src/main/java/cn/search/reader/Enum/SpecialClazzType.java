package cn.search.reader.Enum;

import java.util.HashMap;

public enum SpecialClazzType {

    Byte("B", "byte", (byte) 0),
    Char("C", "char", Character.MIN_VALUE),
    Double("D", "double", 0d),
    Float("F", "float", 0f),
    Int("I", "int", 0),
    Long("J", "long", 0),
    Reference("L", null, null),
    Short("S", "short", 0),
    Boolean("Z", "boolean", false),
    Void("V", "void", null),
    Array("[", null, null),

    ReferenceEnd(";", null, null);

    // 描述符和对应类型的map
    public static final HashMap<String, String> DESCRIPTOR_MAP = new HashMap<>();

    // 描述符和对应包装类的map
    public static final HashMap<String, Object> DEFAULT_MAP = new HashMap<>();

    private String descriptor;

    private String clazzName;

    private Object defaultValue;

    static {
        for (SpecialClazzType specialClazzType : SpecialClazzType.values()) {
            DESCRIPTOR_MAP.put(specialClazzType.descriptor, specialClazzType.clazzName);
            DEFAULT_MAP.put(specialClazzType.descriptor, specialClazzType.defaultValue);
        }
    }

    SpecialClazzType(String descriptor, String clazzName, Object defaultValue) {
        this.descriptor = descriptor;
        this.clazzName = clazzName;
        this.defaultValue = defaultValue;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

}

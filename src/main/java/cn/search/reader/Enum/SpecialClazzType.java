package cn.search.reader.Enum;

import java.util.HashMap;

public enum SpecialClazzType {

    Byte("B", "byte", (byte) 0, byte.class),
    Char("C", "char", Character.MIN_VALUE, char.class),
    Double("D", "double", 0d, double.class),
    Float("F", "float", 0f, float.class),
    Int("I", "int", 0, int.class),
    Long("J", "long", 0, long.class),
    Reference("L", null, null, null),
    Short("S", "short", 0, short.class),
    Boolean("Z", "boolean", false, boolean.class),
    Void("V", "void", null, void.class),
    Array("[", null, null, null),

    ReferenceEnd(";", null, null, null);

    // 描述符和对应类型的map
    public static final HashMap<String, String> DESCRIPTOR_MAP = new HashMap<>();

    // 描述符和对应包装类的map
    public static final HashMap<String, Object> DEFAULT_MAP = new HashMap<>();

    // 原始类名和对应类的map
    public static final HashMap<String, Class> CLASS_TYPE_MAP = new HashMap<>();

    private String descriptor;

    private String clazzName;

    private Object defaultValue;

    private Class type;

    static {
        for (SpecialClazzType specialClazzType : SpecialClazzType.values()) {
            DESCRIPTOR_MAP.put(specialClazzType.descriptor, specialClazzType.clazzName);
            DEFAULT_MAP.put(specialClazzType.descriptor, specialClazzType.defaultValue);
            CLASS_TYPE_MAP.put(specialClazzType.clazzName, specialClazzType.type);
        }
    }

    SpecialClazzType(String descriptor, String clazzName, Object defaultValue, Class type) {
        this.descriptor = descriptor;
        this.clazzName = clazzName;
        this.defaultValue = defaultValue;
        this.type = type;
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

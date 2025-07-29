package cn.search.reader.Enum;

import java.util.HashMap;

public enum SpecialClazzType {

    Byte("B", "byte"),
    Char("C", "char"),
    Double("D", "double"),
    Float("F", "float"),
    Int("I", "int"),
    Long("J", "long"),
    Reference("L", null),
    Short("S", "short"),
    Boolean("Z", "boolean"),
    Void("V", "void"),
    Array("[", null),

    ReferenceEnd(";", null);

    // 描述符和对应类型的map
    public static final HashMap<String, String> DESCRIPTOR_MAP = new HashMap<>();

    private String descriptor;

    private String clazzName;

    static {
        for (SpecialClazzType specialClazzType : SpecialClazzType.values()) {
            DESCRIPTOR_MAP.put(specialClazzType.descriptor, specialClazzType.clazzName);
        }
    }

    SpecialClazzType(String descriptor, String clazzName) {
        this.descriptor = descriptor;
        this.clazzName = clazzName;
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

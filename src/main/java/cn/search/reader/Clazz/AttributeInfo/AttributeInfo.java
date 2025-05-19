package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U2;
import cn.search.reader.Usinged.U4;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Data
@ClazzConstructor
@Slf4j
@Accessors(chain = true)
public class AttributeInfo {

    static HashMap<String, Class<?>> ATTRIBUTE_INFO_MAP = new HashMap<>();

    static {
        ATTRIBUTE_INFO_MAP.put("ConstantValue", ConstantValueAttribute.class);
        ATTRIBUTE_INFO_MAP.put("Code", CodeAttribute.class);
        ATTRIBUTE_INFO_MAP.put("StackMapTable", StackMapTableAttribute.class);
        ATTRIBUTE_INFO_MAP.put("Exceptions", ExceptionsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("InnerClasses", InnerClassesAttribute.class);
        ATTRIBUTE_INFO_MAP.put("EnclosingMethod", EnclosingMethodAttribute.class);
        ATTRIBUTE_INFO_MAP.put("Synthetic", SyntheticAttribute.class);
        ATTRIBUTE_INFO_MAP.put("Signature", SignatureAttribute.class);
        ATTRIBUTE_INFO_MAP.put("SourceFile", SourceFileAttribute.class);
        ATTRIBUTE_INFO_MAP.put("SourceDebugExtension", SourceDebugExtensionAttribute.class);
        ATTRIBUTE_INFO_MAP.put("LineNumberTable", LineNumberTableAttribute.class);
        ATTRIBUTE_INFO_MAP.put("LocalVariableTable", LocalVariableTableAttribute.class);
        ATTRIBUTE_INFO_MAP.put("LocalVariableTypeTable", LocalVariableTypeTableAttribute.class);
        ATTRIBUTE_INFO_MAP.put("Deprecated", DeprecatedAttribute.class);
        ATTRIBUTE_INFO_MAP.put("RuntimeVisibleAnnotations", RuntimeVisibleAnnotationsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("RuntimeInvisibleAnnotations", RuntimeInvisibleAnnotationsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("RuntimeVisibleParameterAnnotations", RuntimeVisibleParameterAnnotationsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("RuntimeInvisibleParameterAnnotations", RuntimeInvisibleParameterAnnotationsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("RuntimeVisibleTypeAnnotations", RuntimeVisibleTypeAnnotationsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("RuntimeInvisibleTypeAnnotations_attribute", RuntimeInvisibleTypeAnnotationsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("AnnotationDefault", AnnotationDefaultAttribute.class);
        ATTRIBUTE_INFO_MAP.put("BootstrapMethods", BootstrapMethodsAttribute.class);
        ATTRIBUTE_INFO_MAP.put("MethodParameters", MethodParametersAttribute.class);
    }

    // u2
    private U2 attributeNameIndex;

    // u4
    private U4 attributeLength;

    private ConstantUtf8Info attributeName;

    public AttributeInfo() {

    }

    public AttributeInfo(DataInputStream dataInput) {

    }

    public static AttributeInfo getAttributeInfoByNameIndex(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        U2 nameIndex = new U2(dataInput);
        U4 attributeLength = new U4(dataInput);
        ConstantUtf8Info constantUtf8Info = (ConstantUtf8Info) constantPool[nameIndex.getValue() - 1];
        try {
            Class<?> attributeSonClass = ATTRIBUTE_INFO_MAP.get(constantUtf8Info.getUtf8Info());
            Constructor<?> constructor = attributeSonClass.getConstructor(DataInputStream.class, ConstantCpInfo[].class);
            return ((AttributeInfo) constructor.newInstance(dataInput, constantPool)).setAttributeNameIndex(nameIndex)
                    .setAttributeLength(attributeLength)
                    .setAttributeName(constantUtf8Info);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("AttributeInfo has no constructor nameIndex is {}", nameIndex.getValue());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;


    }


    public void initConstant(ConstantCpInfo[] constantPool) {

    }


}


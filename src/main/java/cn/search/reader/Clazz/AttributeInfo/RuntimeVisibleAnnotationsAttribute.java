package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class RuntimeVisibleAnnotationsAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 numAnnotations;

    // length = numAnnotations
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private Annotation[] annotations;

    public RuntimeVisibleAnnotationsAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.annotations.length; i++) {
            this.annotations[i].initConstant(constantPool);
        }
    }

}

@Data
@ClazzConstructor
class Annotation {

    // u2
    @ClazzField(order = 0)
    private U2 typeIndex;

    // u2
    @ClazzField(order = 1)
    private U2 numElementValuePairs;

    // length = numElementValuePairs
    @ClazzSelfListField(order = 2, listLenOrder = 1)
    private ElementValuePairs[] elementValuePairs;

    private ConstantUtf8Info type;

    public Annotation(DataInputStream dataInput) {

    }

    public void initConstant(ConstantCpInfo[] constantPool) {

        this.type = (ConstantUtf8Info) constantPool[this.typeIndex.getValue() - 1];
        for (int i = 0; i < this.elementValuePairs.length; i++) {
            elementValuePairs[i].initConstant(constantPool);
        }
    }

}

@Data
@ClazzConstructor
class ElementValuePairs {

    // u2
    @ClazzField(order = 0)
    private U2 elementNameIndex;

    // only one
    private ElementValue value;

    public ElementValuePairs(DataInputStream dataInput) {
        this.value = ElementValue.getElementValueByTag(dataInput);
    }

    public void initConstant(ConstantCpInfo[] constantPool) {
        this.value.initConstant(constantPool);
    }
}

@Data
@Accessors(chain = true)
@Slf4j
class ElementValue {

    static HashMap<String, Class<?>> ELEMENT_VALUE_MAP = new HashMap<>();

    static {
        ELEMENT_VALUE_MAP.put("B", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("C", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("D", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("F", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("I", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("J", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("S", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("Z", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("s", ConstValueIndex.class);
        ELEMENT_VALUE_MAP.put("e", EnumConstValue.class);
        ELEMENT_VALUE_MAP.put("c", ClassInfoIndex.class);
        ELEMENT_VALUE_MAP.put("@", AnnotationValue.class);
        ELEMENT_VALUE_MAP.put("[", ArrayValue.class);
    }

    // u1
    private U1 tag;

    public ElementValue() {

    }

    public ElementValue(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

    public static ElementValue getElementValueByTag(DataInputStream dataInput) {
        U1 tag = new U1(dataInput);
        byte[] temp = new byte[1];
        temp[0] = (byte) tag.getValue();
        try {
            Class<?> sonClass = ELEMENT_VALUE_MAP.get(new String(temp));
            Constructor<?> constructor = sonClass.getConstructor(DataInputStream.class);
            return ((ElementValue) constructor.newInstance(dataInput)).setTag(tag);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("ElementValue has no constructor tag is {}", tag.getValue());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initConstant(ConstantCpInfo[] constantPool) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class ConstValueIndex extends ElementValue {

    // tag = 'B' or 'C' or 'D' or 'F' or 'I' or 'J' or 'S' or 'Z' or 's'

    // u2
    @ClazzField(order = 0)
    private U2 constValueIndex;

    private ConstantCpInfo constValue;

    public ConstValueIndex(DataInputStream dataInput) {

    }

    @Override
    public void initConstant(ConstantCpInfo[] constantPool) {
        this.constValue = constantPool[this.constValueIndex.getValue() - 1];
    }
}


@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class EnumConstValue extends ElementValue {

    // tag = 'e'

    // u2
    @ClazzField(order = 0)
    private U2 typeNameIndex;

    // u2
    @ClazzField(order = 1)
    private U2 constNameIndex;

    private ConstantUtf8Info typeName;

    private ConstantUtf8Info constName;

    public EnumConstValue(DataInputStream dataInput) {

    }

    @Override
    public void initConstant(ConstantCpInfo[] constantPool) {
        this.typeName = (ConstantUtf8Info) constantPool[this.typeNameIndex.getValue() - 1];
        this.constName = (ConstantUtf8Info) constantPool[this.constNameIndex.getValue() - 1];
    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class ClassInfoIndex extends ElementValue {

    // tag = 'c'

    // u2
    @ClazzField(order = 0)
    private U2 classInfoIndex;

    private ConstantUtf8Info classInfo;

    public ClassInfoIndex(DataInputStream dataInput) {

    }

    @Override
    public void initConstant(ConstantCpInfo[] constantPool) {
        this.classInfo = (ConstantUtf8Info) constantPool[this.classInfoIndex.getValue() - 1];
    }

}

@Data
@EqualsAndHashCode(callSuper = true)
class AnnotationValue extends ElementValue {

    // tag = '@'

    // only one
    @ClazzField(order = 0)
    private Annotation annotationValue;


    public AnnotationValue(DataInputStream dataInput) {

    }

    @Override
    public void initConstant(ConstantCpInfo[] constantPool) {
        this.annotationValue.initConstant(constantPool);
    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class ArrayValue extends ElementValue {

    // tag = '['

    // u2
    @ClazzField(order = 0)
    private U2 numValues;

    // length = numValues
    private ElementValue[] values;

    public ArrayValue(DataInputStream dataInput) {

        for (int i = 0; i < values.length; i++) {
            values[i] = ElementValue.getElementValueByTag(dataInput);
        }
    }

    @Override
    public void initConstant(ConstantCpInfo[] constantPool) {
        for (int i = 0; i < values.length; i++) {
            values[i].initConstant(constantPool);
        }
    }

}



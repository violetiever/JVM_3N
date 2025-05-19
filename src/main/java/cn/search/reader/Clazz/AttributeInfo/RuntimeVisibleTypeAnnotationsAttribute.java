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
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class RuntimeVisibleTypeAnnotationsAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 numAnnotations;

    // length = numAnnotations
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private TypeAnnotation[] annotations;

    public RuntimeVisibleTypeAnnotationsAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.annotations.length; i++) {
            this.annotations[i].initConstant(constantPool);
        }
    }

}

@Data
@Accessors(chain = true)
class TypeAnnotation {


    // u1
    private U1 targetType;

    // only one
    private TargetInfo targetInfo;

    // only one
    private TypePath targetPath;

    // u2
    private U2 typeIndex;

    // u2
    private U2 numElementValuePairs;

    // length = numElementValuePairs
    private ElementValuePairs[] elementValuePairs;


    private ConstantUtf8Info type;

    public TypeAnnotation(DataInputStream dataInput) {
        this.targetType = new U1(dataInput);
        this.targetInfo = TargetInfo.getTargetInfoByTargetType(dataInput, targetType);
        this.targetPath = new TypePath(dataInput);
        this.typeIndex = new U2(dataInput);
        this.numElementValuePairs = new U2(dataInput);
        this.elementValuePairs = new ElementValuePairs[this.numElementValuePairs.getValue()];
        for (int i = 0; i < elementValuePairs.length; i++) {
            elementValuePairs[i] = new ElementValuePairs(dataInput);
        }

    }

    public void initConstant(ConstantCpInfo[] constantPool) {

        this.type = (ConstantUtf8Info) constantPool[this.typeIndex.getValue() - 1];
        for (int i = 0; i < this.elementValuePairs.length; i++) {
            elementValuePairs[i].initConstant(constantPool);
        }
    }


}

@Data
@Slf4j
class TargetInfo {

    static HashMap<Integer, Class<?>> TARGET_INFO_MAP = new HashMap<>();

    static {
        TARGET_INFO_MAP.put(0x00, TypeParameterTarget.class);
        TARGET_INFO_MAP.put(0x01, TypeParameterTarget.class);
        TARGET_INFO_MAP.put(0x10, SupertypeTarget.class);
        TARGET_INFO_MAP.put(0x11, TypeParameterBoundTarget.class);
        TARGET_INFO_MAP.put(0x12, TypeParameterBoundTarget.class);
        TARGET_INFO_MAP.put(0x13, EmptyTarget.class);
        TARGET_INFO_MAP.put(0x14, EmptyTarget.class);
        TARGET_INFO_MAP.put(0x15, EmptyTarget.class);
        TARGET_INFO_MAP.put(0x16, FormalParameterTarget.class);
        TARGET_INFO_MAP.put(0x17, ThrowsTarget.class);
        TARGET_INFO_MAP.put(0x40, LocalvarTarget.class);
        TARGET_INFO_MAP.put(0x41, LocalvarTarget.class);
        TARGET_INFO_MAP.put(0x42, CatchTarget.class);
        TARGET_INFO_MAP.put(0x43, OffsetTarget.class);
        TARGET_INFO_MAP.put(0x44, OffsetTarget.class);
        TARGET_INFO_MAP.put(0x45, OffsetTarget.class);
        TARGET_INFO_MAP.put(0x46, OffsetTarget.class);
        TARGET_INFO_MAP.put(0x47, TypeArgumentTarget.class);
        TARGET_INFO_MAP.put(0x48, TypeArgumentTarget.class);
        TARGET_INFO_MAP.put(0x49, TypeArgumentTarget.class);
        TARGET_INFO_MAP.put(0x4A, TypeArgumentTarget.class);
        TARGET_INFO_MAP.put(0x4B, TypeArgumentTarget.class);
    }

    public static TargetInfo getTargetInfoByTargetType(DataInputStream dataInput, U1 targetType) {
        try {
            Class<?> sonClass = TARGET_INFO_MAP.get(targetType.getValue());
            Constructor<?> constructor = sonClass.getConstructor(DataInputStream.class);
            return ((TargetInfo) constructor.newInstance(dataInput));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("TypeAnnotation has no constructor targetType is {}", targetType.getValue());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class TypeParameterTarget extends TargetInfo {

    // u1
    @ClazzField(order = 0)
    private U1 typeParameterIndex;

    public TypeParameterTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class SupertypeTarget extends TargetInfo {

    // u2
    @ClazzField(order = 0)
    private U2 supertypeIndex;

    public SupertypeTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class TypeParameterBoundTarget extends TargetInfo {

    // u1
    @ClazzField(order = 0)
    private U1 typeParameterIndex;

    // u1
    @ClazzField(order = 1)
    private U1 boundIndex;

    public TypeParameterBoundTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
class EmptyTarget extends TargetInfo {

    public EmptyTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class FormalParameterTarget extends TargetInfo {

    // u1
    @ClazzField(order = 0)
    private U1 formalParameterIndex;

    public FormalParameterTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class ThrowsTarget extends TargetInfo {

    // u2
    @ClazzField(order = 0)
    private U2 throwsTypeIndex;

    public ThrowsTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class LocalvarTarget extends TargetInfo {

    // u2
    @ClazzField(order = 0)
    private U2 tableLength;

    // length = tableLength
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private Table[] table;

    public LocalvarTarget(DataInputStream dataInput) {

    }

}

@Data
@ClazzConstructor
class Table {

    // u2
    @ClazzField(order = 0)
    private U2 startPc;

    // u2
    @ClazzField(order = 1)
    private U2 length;

    // u2
    @ClazzField(order = 2)
    private U2 index;

    public Table(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class CatchTarget extends TargetInfo {

    // u2
    @ClazzField(order = 0)
    private U2 exceptionTableIndex;

    public CatchTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class OffsetTarget extends TargetInfo {

    // u2
    @ClazzField(order = 0)
    private U2 offset;

    public OffsetTarget(DataInputStream dataInput) {

    }

}

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
class TypeArgumentTarget extends TargetInfo {

    // u2
    @ClazzField(order = 0)
    private U2 offset;

    // u1
    @ClazzField(order = 1)
    private U1 typeArgumentIndex;

    public TypeArgumentTarget(DataInputStream dataInput) {

    }

}

@Data
@ClazzConstructor
class TypePath {

    // u1
    @ClazzField(order = 0)
    private U1 pathLength;

    // length = pathLength
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private Path[] path;

    public TypePath(DataInputStream dataInput) {

    }

}

@Data
@ClazzConstructor
class Path {

    // u1
    @ClazzField(order = 0)
    private U1 typePathKind;

    // u1
    @ClazzField(order = 1)
    private U1 typeArgumentIndex;

    public Path(DataInputStream dataInput) {

    }

}

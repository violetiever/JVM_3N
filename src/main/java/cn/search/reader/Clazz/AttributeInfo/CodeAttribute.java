package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.*;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Arrays;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class CodeAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 maxStack;

    // u2
    @ClazzField(order = 1)
    private U2 maxLocals;

    // u4
    @ClazzField(order = 2)
    private U4 codeLength;

    // length = codeLength each code is u1
    @ClazzSelfListField(order = 3, listLenOrder = 2)
    private U1[] code;

    // u2
    @ClazzField(order = 4)
    private U2 exceptionTableLength;

    // length = exceptionTableLength
    @ClazzSelfListField(order = 5, listLenOrder = 4)
    private ExceptionTable[] exceptionTable;

    // u2
    @ClazzField(order = 6)
    private U2 attributesCount;

    // length = attributesCount
    @ClazzListField(order = 7, listLenOrder = 6, listFieldFactory = "getAttributeInfoByNameIndex")
    private AttributeInfo[] attributes;


    public CodeAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {
        for (ExceptionTable table : exceptionTable) {
            int catchTypeIndex = table.getCatchType().getValue();
            if (catchTypeIndex != 0)
                table.setCatchClass((ConstantClassInfo) constantPool[catchTypeIndex - 1]);
        }
    }

    public AttributeInfo getAttributeByClass(Class clazz){
        for (AttributeInfo attribute : this.attributes)
            if(attribute.getClass() == clazz)
                return attribute;
        return null;
    }

    @Override
    public String toString() {
        return "CodeAttribute{" + "\n" +
                "code=" + Arrays.toString(code) + "\n" +
                ", exceptionTable=" + Arrays.toString(exceptionTable) + "\n" +
                ", attributes=" + Arrays.toString(attributes) + "\n" +
                '}';
    }

}

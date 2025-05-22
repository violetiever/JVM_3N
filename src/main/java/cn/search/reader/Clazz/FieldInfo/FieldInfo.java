package cn.search.reader.Clazz.FieldInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.AttributeInfo.AttributeInfo;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U2;
import lombok.Data;

import java.io.DataInputStream;

@Data
@ClazzConstructor
public class FieldInfo {

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


    public FieldInfo() {

    }

    public FieldInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        this.accessFlag = Clazz.resolveAccessFlag(this.accessFlags);

        this.name = (ConstantUtf8Info) constantPool[this.nameIndex.getValue() -1];

        this.descriptor = (ConstantUtf8Info) constantPool[this.descriptorIndex.getValue() -1];

        this.attributes = new AttributeInfo[this.attributesCount.getValue()];
        for (int i = 0; i < this.attributes.length; i++) {
            attributes[i] = AttributeInfo.getAttributeInfoByNameIndex(dataInput, constantPool);
        }

    }

}


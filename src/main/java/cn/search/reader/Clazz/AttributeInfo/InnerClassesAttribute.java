package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantClassInfo;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import static cn.search.reader.Clazz.Clazz.ACCESS_FLAGS_MAP;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class InnerClassesAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 numberOfClasses;

    // length = numberOfClasses
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private Classes[] classes;

    public InnerClassesAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

        for (int i = 0; i < this.classes.length; i++) {
            classes[i].initConstant(constantPool);
        }

    }

}

@Data
@ClazzConstructor
class Classes {

    // u2
    @ClazzField(order = 0)
    private U2 innerClassInfoIndex;

    // u2
    @ClazzField(order = 1)
    private U2 outerClassInfoIndex;

    // u2
    @ClazzField(order = 2)
    private U2 innerNameIndex;

    // u2
    @ClazzField(order = 3)
    private U2 innerClassAccessFlages;

    private ConstantClassInfo innerClassInfo;

    private ConstantClassInfo outerClassInfo;

    private ConstantUtf8Info innerName;

    private String[] innerClassAccess;

    public Classes(DataInputStream dataInput) {

    }

    public void initConstant(ConstantCpInfo[] constantPool) {
        this.innerClassInfo = (ConstantClassInfo) constantPool[this.innerClassInfoIndex.getValue() - 1];
        if (this.outerClassInfoIndex.getValue() != 0)
            this.outerClassInfo = (ConstantClassInfo) constantPool[this.outerClassInfoIndex.getValue() - 1];
        if (this.innerNameIndex.getValue() != 0)
            this.innerName = (ConstantUtf8Info) constantPool[this.innerNameIndex.getValue() - 1];

        this.innerClassAccess = Clazz.resolveAccessFlag(this.innerClassAccessFlages);

    }


}
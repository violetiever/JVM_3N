package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.Annotation.ClazzSelfListField;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U1;
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
public class MethodParametersAttribute extends AttributeInfo {

    // u1
    @ClazzField(order = 0)
    private U1 parametersCount;

    // length = parametersCount
    @ClazzSelfListField(order = 1, listLenOrder = 0)
    private Parameters[] parameters;

    public MethodParametersAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

}

@Data
@ClazzConstructor
class Parameters {

    // u2
    @ClazzField(order = 0)
    private U2 nameIndex;

    // u2
    @ClazzField(order = 1)
    private U2 accessFlags;

    private ConstantUtf8Info name;

    private String[] accessFlag;


    public Parameters(DataInputStream dataInput) {

    }

    public void initConstant(ConstantCpInfo[] constantPool) {
        int nameIndexValue = this.nameIndex.getValue();
        if (nameIndexValue != 0) {
            this.name = (ConstantUtf8Info) constantPool[nameIndexValue - 1];
        }
        this.accessFlag = Clazz.resolveAccessFlag(this.accessFlags);

    }

}

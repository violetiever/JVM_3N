package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantValueAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 constantValueIndex;

    //@ClazzCpInfoInit(order = 1, initOrder = 0)
    private ConstantCpInfo constantValue;

    public ConstantValueAttribute(DataInputStream dataInput,ConstantCpInfo[] constantPool) {

    }

}

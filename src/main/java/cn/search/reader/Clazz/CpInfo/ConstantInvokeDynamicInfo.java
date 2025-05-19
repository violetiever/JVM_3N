package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantInvokeDynamicInfo extends ConstantCpInfo {

    // todo 解析这个字段
    // u2
    @ClazzField(order = 0)
    private U2 bootstrapMethodAttrIndex;

    // u2
    @ClazzField(order = 1)
    private U2 nameAndTypeIndex;

    @ClazzCpInfoInit(order = 2, initOrder = 1)
    private ConstantNameAndTypeInfo nameAndType;

    public ConstantInvokeDynamicInfo(DataInputStream dataInput) {

    }

}

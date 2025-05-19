package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantMethodHandleInfo extends ConstantCpInfo {

    // todo 需要解析这个类
    // u1
    @ClazzField(order = 0)
    private U1 referenceKind;

    // u2
    @ClazzField(order = 1)
    private U2 referenceIndex;

    public ConstantMethodHandleInfo(DataInputStream dataInput) {

    }

}

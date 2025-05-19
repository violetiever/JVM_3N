package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U4;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantLongInfo extends ConstantCpInfo {


    // u4
    @ClazzField(order = 0)
    private U4 highBytes;

    // u4
    @ClazzField(order = 1)
    private U4 lowBytes;

    private Long longValue;

    public ConstantLongInfo(DataInputStream dataInput) {
        longValue =  ((long) highBytes.getValue() << 32) + lowBytes.getValue();
    }

}

package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U4;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantIntegerInfo extends ConstantCpInfo {

    // u4
    @ClazzField(order = 0)
    private U4 bytes;

    private Long integerValue;

    public ConstantIntegerInfo(DataInputStream dataInput) {
        integerValue = bytes.getValue();
    }

    @Override
    public void invoke() {
        if (Objects.isNull(this.invokeObject))
            this.invokeObject = this.integerValue;
    }
}

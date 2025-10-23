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
public class ConstantFloatInfo extends ConstantCpInfo {

    // u4
    @ClazzField(order = 0)
    private U4 bytes;

    private Float floatValue;

    public ConstantFloatInfo(DataInputStream dataInput) {
        long bits = this.bytes.getValue();

        if (bits == 0x7f800000) {
            this.floatValue = Float.MAX_VALUE;
            return;
        } else if (bits == 0xff800000) {
            this.floatValue = Float.MIN_VALUE;
            return;
        } else if ((bits >= 0x7f800001 && bits <= 0x7fffffff) || (bits >= 0xff800001 && bits <= 0xffffffff)) {
            this.floatValue = Float.NaN;
            return;
        }

        long s = ((bits >> 31) == 0) ? 1 : -1;
        long e = (bits >> 23) & 0xff;
        long m = (e == 0) ?
                (bits & 0x7fffff) << 1 :
                (bits & 0x7fffff) | 0x800000;

        this.floatValue = (float) (s * m * 2 ^ (e - 150));
    }

    @Override
    public void invoke() {
        if (Objects.isNull(this.invokeObject))
            this.invokeObject = this.floatValue;
    }

}

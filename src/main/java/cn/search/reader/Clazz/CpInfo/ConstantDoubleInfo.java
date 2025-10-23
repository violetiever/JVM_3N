package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U4;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = false)
@ClazzConstructor
public class ConstantDoubleInfo extends ConstantCpInfo {

    // u4
    @ClazzField(order = 0)
    private U4 highBytes;

    // u4
    @ClazzField(order = 1)
    private U4 lowBytes;

    private Double doubleValue;

    public ConstantDoubleInfo(DataInputStream dataInput) {
        long bits = ((long) highBytes.getValue() << 32) + lowBytes.getValue();

        if (bits == 0x7ff0000000000000L) {
            this.doubleValue = Double.MAX_VALUE;
            return;
        } else if (bits == 0xfff0000000000000L) {
            this.doubleValue = Double.MIN_VALUE;
            return;
        } else if ((bits >= 0x7ff0000000000001L && bits <= 0x7fffffffffffffffL)
                || (bits >= 0xfff0000000000001L && bits <= 0xffffffffffffffffL)) {
            this.doubleValue = Double.NaN;
            return;
        }

        int s = ((bits >> 63) == 0) ? 1 : -1;
        int e = (int) ((bits >> 52) & 0x7ffL);
        long m = (e == 0) ?
                (bits & 0xfffffffffffffL) << 1 :
                (bits & 0xfffffffffffffL) | 0x10000000000000L;
        this.doubleValue = (double) (s * m * 2 ^ (e - 1075));
    }

    @Override
    public void invoke() {
        if (Objects.isNull(this.invokeObject))
            this.invokeObject = this.doubleValue;
    }

}

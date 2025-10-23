package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.*;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantValueAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 constantValueIndex;

    //@ClazzCpInfoInit(order = 1, initOrder = 0)
    private ConstantCpInfo constantValue;

    public ConstantValueAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool) {
        this.constantValue = constantPool[constantValueIndex.getValue() - 1];
    }

    public Object getConstantValue() {
        if (this.constantValue instanceof ConstantLongInfo) {
            return ((ConstantLongInfo) constantValue).getLongValue();
        } else if (this.constantValue instanceof ConstantFloatInfo) {
            return ((ConstantFloatInfo) constantValue).getFloatValue();
        } else if (this.constantValue instanceof ConstantDoubleInfo) {
            return ((ConstantDoubleInfo) constantValue).getDoubleValue();
        } else if (this.constantValue instanceof ConstantIntegerInfo) {
            return ((ConstantIntegerInfo) constantValue).getIntegerValue();
        } else if (this.constantValue instanceof ConstantStringInfo) {
            return ((ConstantStringInfo) constantValue).getString().getUtf8Info();
        }
        return null;
    }

}

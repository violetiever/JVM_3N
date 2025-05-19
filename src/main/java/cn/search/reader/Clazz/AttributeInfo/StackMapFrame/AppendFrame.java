package cn.search.reader.Clazz.AttributeInfo.StackMapFrame;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo.VerificationTypeInfo;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class AppendFrame extends StackMapFrame {

    // frameType = APPEND 252-254

    private Integer k;

    // length = frameType - 251
    private VerificationTypeInfo[] stack;

    public AppendFrame(DataInputStream dataInput, ConstantCpInfo[] constantPool, U1 frameType) {
        super.setFrameType(frameType);
        this.setOffsetDelta(new U2(dataInput));
        k = this.getFrameType().getValue() - 251;
        this.stack = new VerificationTypeInfo[this.getFrameType().getValue() - 251];
        for (int i = 0; i < stack.length; i++) {
            this.stack[i] = VerificationTypeInfo.getVerificationTypeInfoByTag(dataInput, constantPool);
        }
    }

}

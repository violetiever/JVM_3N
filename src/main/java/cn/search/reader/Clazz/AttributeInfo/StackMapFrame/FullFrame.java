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
public class FullFrame extends StackMapFrame {

    // frameType = FULL_FRAME 255


    // u2

    private U2 numberOfLocals;

    // length = numberOfLocals
    private VerificationTypeInfo[] locals;

    // u2
    private U2 numberOfStackItems;

    // length = numberOfStackItems
    private VerificationTypeInfo[] stack;

    public FullFrame(DataInputStream dataInput, ConstantCpInfo[] constantPool, U1 frameType) {
        super.setFrameType(frameType);
        this.setOffsetDelta(new U2(dataInput));
        this.numberOfLocals = new U2(dataInput);

        int len = this.numberOfLocals.getValue();
        this.locals = new VerificationTypeInfo[len];
        for (int i = 0; i < len; i++) {
            this.locals[i] = VerificationTypeInfo.getVerificationTypeInfoByTag(dataInput, constantPool);
//            this.locals[i] = new VerificationTypeInfo(dataInput);

        }

        this.numberOfStackItems = new U2(dataInput);

        int len2 = this.numberOfStackItems.getValue();
        this.stack = new VerificationTypeInfo[len2];
        for (int i = 0; i < len2; i++) {
            this.stack[i] = VerificationTypeInfo.getVerificationTypeInfoByTag(dataInput, constantPool);
        }

    }

}

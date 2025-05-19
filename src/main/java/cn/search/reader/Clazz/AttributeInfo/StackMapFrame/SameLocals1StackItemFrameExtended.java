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
public class SameLocals1StackItemFrameExtended extends StackMapFrame{

    // frameType = SAME_LOCALS_1_STACK_ITEM_EXTENDED 247



    // length = 1
    private VerificationTypeInfo[] stack;

    public SameLocals1StackItemFrameExtended(DataInputStream dataInput, ConstantCpInfo[] constantPool, U1 frameType) {
        super.setFrameType(frameType);
        this.setOffsetDelta(new U2(dataInput));

        this.stack = new VerificationTypeInfo[1];
        for (int i = 0; i < 1; i++) {
            this.stack[i] =  VerificationTypeInfo.getVerificationTypeInfoByTag(dataInput,constantPool);
        }
    }

}

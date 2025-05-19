package cn.search.reader.Clazz.AttributeInfo.StackMapFrame;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class SameFrameExtended extends StackMapFrame{

    // frameType = SAME_FRAME_EXTENDED 251

    public SameFrameExtended(DataInputStream dataInput, ConstantCpInfo[] constantPool, U1 frameType) {
        super.setFrameType(frameType);
        this.setOffsetDelta(new U2(dataInput));
    }

}

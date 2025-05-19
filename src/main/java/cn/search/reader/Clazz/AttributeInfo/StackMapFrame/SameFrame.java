package cn.search.reader.Clazz.AttributeInfo.StackMapFrame;

import cn.search.Annotation.ClazzConstructor;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class SameFrame extends StackMapFrame{

    // frameType = SAME 0-63


    public SameFrame(DataInputStream dataInput, ConstantCpInfo[] constantPool, U1 frameType) {
        super.setFrameType(frameType);
        this.setOffsetDelta(new U2(this.getFrameType().getValue()));
    }

}

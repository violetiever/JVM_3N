package cn.search.reader.Clazz.AttributeInfo.StackMapFrame;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


@Data
@ClazzConstructor
@Accessors(chain = true)
@Slf4j
public class StackMapFrame {


    // u1
    private U1 frameType;

    private U2 offsetDelta;

    public StackMapFrame() {

    }

    public static StackMapFrame getFrameByType(DataInputStream dataInput, ConstantCpInfo[] constantPool) {
        U1 frameType = new U1(dataInput);
        int frameTypeValue = frameType.getValue();
        Class<?> sonClazz = null;
        if (frameTypeValue >= 0 && frameTypeValue <= 63) {
            sonClazz = SameFrame.class;
        } else if (frameTypeValue >= 64 && frameTypeValue <= 127) {
            sonClazz = SameLocals1StackItemFrame.class;
        } else if (frameTypeValue == 247) {
            sonClazz = SameLocals1StackItemFrameExtended.class;
        } else if (frameTypeValue >= 248 && frameTypeValue <= 250) {
            sonClazz = ChopFrame.class;
        } else if (frameTypeValue == 251) {
            sonClazz = SameFrameExtended.class;
        } else if (frameTypeValue >= 252 && frameTypeValue <= 254) {
            sonClazz = AppendFrame.class;
        } else if (frameTypeValue == 255) {
            sonClazz = FullFrame.class;
        } else {
            return null;
        }
        try {
            Constructor<?> constructor = sonClazz.getConstructor(DataInputStream.class, ConstantCpInfo[].class, U1.class);
            return ((StackMapFrame) constructor.newInstance(dataInput, constantPool, frameType));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("StackMapFrame has no constructor frameType is {}", frameType.getValue());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}

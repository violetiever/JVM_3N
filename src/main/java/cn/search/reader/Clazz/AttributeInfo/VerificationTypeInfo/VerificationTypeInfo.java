package cn.search.reader.Clazz.AttributeInfo.VerificationTypeInfo;

import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@Data
@Accessors(chain = true)
@Slf4j
public class VerificationTypeInfo {

    static HashMap<Integer, Class<?>> VERIFICATION_TYPE_INFO_MAP = new HashMap<>();

    static {
        VERIFICATION_TYPE_INFO_MAP.put(0, TopVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(1, IntegerVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(2, FloatVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(3, DoubleVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(4, LongVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(5, NullVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(6, UninitializedThisVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(7, ObjectVariableInfo.class);
        VERIFICATION_TYPE_INFO_MAP.put(8, UninitializedVariableInfo.class);
    }

    // u1
    private U1 tag;

    public VerificationTypeInfo() {

    }

    public VerificationTypeInfo(DataInputStream dataInput, ConstantCpInfo[] constantPool) {

    }

    public static VerificationTypeInfo getVerificationTypeInfoByTag(DataInputStream dataInput, ConstantCpInfo[] constantPool) {
        U1 tag = new U1(dataInput);
        try {
            Class<?> sonClass = VERIFICATION_TYPE_INFO_MAP.get(tag.getValue());
            Constructor<?> constructor = sonClass.getConstructor(DataInputStream.class, ConstantCpInfo[].class);
            return ((VerificationTypeInfo) constructor.newInstance(dataInput, constantPool)).setTag(tag);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("VerificationTypeInfo has no constructor tag is {}", tag.getValue());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}

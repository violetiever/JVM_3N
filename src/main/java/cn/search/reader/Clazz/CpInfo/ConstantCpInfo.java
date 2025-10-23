package cn.search.reader.Clazz.CpInfo;

import cn.search.reader.Clazz.Clazz;
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
public class ConstantCpInfo {

    static HashMap<Integer, Class<?>> CP_INFO_MAP = new HashMap<>();

    static {
        CP_INFO_MAP.put(7, ConstantClassInfo.class);
        CP_INFO_MAP.put(9, ConstantFieldRefInfo.class);
        CP_INFO_MAP.put(10, ConstantMethodRefInfo.class);
        CP_INFO_MAP.put(11, ConstantInterfaceMethodRefInfo.class);
        CP_INFO_MAP.put(8, ConstantStringInfo.class);
        CP_INFO_MAP.put(3, ConstantIntegerInfo.class);
        CP_INFO_MAP.put(4, ConstantFloatInfo.class);
        CP_INFO_MAP.put(5, ConstantLongInfo.class);
        CP_INFO_MAP.put(6, ConstantDoubleInfo.class);
        CP_INFO_MAP.put(12, ConstantNameAndTypeInfo.class);
        CP_INFO_MAP.put(1, ConstantUtf8Info.class);
        CP_INFO_MAP.put(15, ConstantMethodHandleInfo.class);
        CP_INFO_MAP.put(16, ConstantMethodTypeInfo.class);
        CP_INFO_MAP.put(18, ConstantInvokeDynamicInfo.class);
    }

    private U1 tag;

    private Clazz thisClazz;

    protected Object invokeObject;

    public ConstantCpInfo() {

    }


    public static ConstantCpInfo getCpInfoByTag(DataInputStream dataInput, ConstantCpInfo[] constantPool, Clazz thisClazz) {
        U1 tag = new U1(dataInput);
        try {
            Class<?> cpSonClass = CP_INFO_MAP.get(tag.getValue());
            Constructor<?> constructor = cpSonClass.getConstructor(DataInputStream.class);
            return ((ConstantCpInfo) constructor.newInstance(dataInput)).setTag(tag).setThisClazz(thisClazz);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("CpInfo has no constructor tag is {}", tag.getValue());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initConstant(ConstantCpInfo[] constantPool) {

    }

    /**
     * 链接，将符号引用转换为直接引用
     */
    public void link() {

    }

    // 验证
    public void verify() {

    }

    // 准备
    public void prepare() {

    }

    // 解析
    public void resolve() {

    }

    // 调用，解析对象到invokeObject内
    public void invoke() {

    }

}


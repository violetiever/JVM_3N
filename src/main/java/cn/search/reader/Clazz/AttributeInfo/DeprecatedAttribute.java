package cn.search.reader.Clazz.AttributeInfo;

import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeprecatedAttribute extends AttributeInfo {

    public DeprecatedAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool){

    }

}

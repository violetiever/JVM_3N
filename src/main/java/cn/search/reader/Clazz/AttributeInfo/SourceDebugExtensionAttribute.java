package cn.search.reader.Clazz.AttributeInfo;

import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Usinged.U1;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
public class SourceDebugExtensionAttribute extends AttributeInfo {

    // length = attributeLength each debugExtension is u1
    private U1[] debugExtension;

    public SourceDebugExtensionAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool){

        this.debugExtension = new U1[(int) this.getAttributeLength().getValue()];
        for (int i = 0; i < debugExtension.length; i++) {
            debugExtension[i] = new U1(dataInput);
        }

    }

}

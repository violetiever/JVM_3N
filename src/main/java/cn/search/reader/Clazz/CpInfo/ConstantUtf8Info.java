package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U1;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
@Accessors(chain = true)
public class ConstantUtf8Info extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 length;

    // length = length each byte is u1
    private U1[] bytes;

    private String utf8Info;

    public ConstantUtf8Info(){

    }

    public ConstantUtf8Info(DataInputStream dataInput) {
        int len = this.length.getValue();
        this.bytes = new U1[len];
        byte[] temp = new byte[len];
        for (int i = 0; i < len; i++) {
            this.bytes[i] = new U1(dataInput);
            temp[i] = (byte) bytes[i].getValue();
        }
        this.utf8Info = new String(temp);

    }



}

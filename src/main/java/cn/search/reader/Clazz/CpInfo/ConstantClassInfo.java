package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.ClassFileReader;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Usinged.U2;
import cn.search.runtime.MethodArea;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantClassInfo extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 nameIndex;

    @ClazzCpInfoInit(order = 1, initOrder = 0)
    private ConstantUtf8Info name;

    public ConstantClassInfo(DataInputStream dataInput) {

    }

    public void link(){
        InputStream classFileInputStream = MethodArea.LAZY_CLAZZ_MAP.get(name.getUtf8Info());
        if(classFileInputStream != null && !MethodArea.CLAZZ_MAP.containsKey(name.getUtf8Info()) ) {
            MethodArea.CLAZZ_MAP.put(name.getUtf8Info(), null);
            Clazz entryClazz = ClassFileReader.ClassFileToClazz(classFileInputStream);
            try {
                classFileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MethodArea.CLAZZ_MAP.remove(name.getUtf8Info());
            MethodArea.CLAZZ_MAP.put(name.getUtf8Info(), entryClazz);
        }
    }

}

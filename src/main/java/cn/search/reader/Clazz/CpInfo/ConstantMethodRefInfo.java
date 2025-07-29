package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantMethodRefInfo extends ConstantRefInfo {

    public ConstantMethodRefInfo(DataInputStream dataInput) {
        super(dataInput);
    }

}

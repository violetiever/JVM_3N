package cn.search.reader.Clazz.AttributeInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.CpInfo.ConstantCpInfo;
import cn.search.reader.Clazz.CpInfo.ConstantUtf8Info;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class SourceFileAttribute extends AttributeInfo {

    // u2
    @ClazzField(order = 0)
    private U2 sourceFileIndex;

    private ConstantUtf8Info sourceFile;

    public SourceFileAttribute(DataInputStream dataInput, ConstantCpInfo[] constantPool){

        this.sourceFile = (ConstantUtf8Info) constantPool[this.sourceFileIndex.getValue() -1];

    }

}

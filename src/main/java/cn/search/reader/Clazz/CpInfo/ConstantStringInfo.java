package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Usinged.U2;
import cn.search.runtime.Heap;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.DataInputStream;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
public class ConstantStringInfo extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 stringIndex;

    @ClazzCpInfoInit(order = 1, initOrder = 0)
    private ConstantUtf8Info string;

    /**
     * 字符串常量池中的索引
     */
    private Integer index;

    public ConstantStringInfo(DataInputStream dataInput) {


    }

    @Override
    public void link() {
        // 放入常量池
        index = Heap.putIntoConstantStringPool(string.getUtf8Info());
    }

    @Override
    public void invoke() {
        if (Objects.isNull(this.invokeObject)) {
            String utf8Info = string.getUtf8Info();
            this.invokeObject = utf8Info;
        }
    }

}

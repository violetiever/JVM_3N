package cn.search.reader.Clazz.CpInfo;

import cn.search.Annotation.ClazzConstructor;
import cn.search.Annotation.ClazzCpInfoInit;
import cn.search.Annotation.ClazzField;
import cn.search.reader.Clazz.Clazz;
import cn.search.reader.ClazzLoader.ClazzLoader;
import cn.search.reader.Usinged.U2;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.DataInputStream;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@ClazzConstructor
@Accessors(chain = true)
public class ConstantClassInfo extends ConstantCpInfo {

    // u2
    @ClazzField(order = 0)
    private U2 nameIndex;

    @ClazzCpInfoInit(order = 1, initOrder = 0)
    private ConstantUtf8Info name;

    private Clazz clazzInfo;

    public ConstantClassInfo() {

    }

    public ConstantClassInfo(DataInputStream dataInput) {

    }

    public void link() {

    }

    @Override
    public String toString() {
        return "ConstantClassInfo{" +
                "name=" + name +
                '}';
    }

    public void resolve() {
        if(Objects.isNull(this.clazzInfo)){
            String clazzName = this.getName().getUtf8Info();
            this.clazzInfo = ClazzLoader.loadClazzByLoader(clazzName, this.getThisClazz().getClazzLoader());
        }
    }

    // invokeObject的类型是Class
    @Override
    public void invoke() {
        try {
            if (Objects.isNull(this.invokeObject)) {
                Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(name.getUtf8Info());
                this.invokeObject = aClass;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

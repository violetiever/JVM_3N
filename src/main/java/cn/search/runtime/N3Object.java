package cn.search.runtime;

import cn.search.reader.Clazz.Clazz;
import cn.search.reader.Clazz.FieldInfo.FieldInfo;
import lombok.Data;

import java.util.Objects;

@Data
public class N3Object {

    private Clazz thisClazz;

    private Clazz superClazz;

    private N3Object superObject;

    private FieldInfo[] fieldInfos;


    public FieldInfo getFieldByNameAndDescriptor(String fieldName, String descriptor) {
        FieldInfo fieldInfo = null;
        N3Object object = this;
        while (Objects.isNull(fieldInfo)) {

            for (FieldInfo field : object.fieldInfos)
                if (fieldName.equals(field.getName().getUtf8Info()) && descriptor.equals(field.getDescriptor().getUtf8Info())) {
                    fieldInfo = field;
                    break;
                }
            if(Objects.isNull(object.superObject)) break;
            object = object.superObject;
        }
        return fieldInfo;
    }

    @Override
    public String toString() {
        return "N3Object(" + thisClazz.getName() + ')';
    }

}

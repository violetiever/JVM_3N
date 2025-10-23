package cn.search.reader.Usinged;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.IOException;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class U2 extends U {

    private int value;

    public U2(DataInputStream dataInput) {
        try {
            this.value = dataInput.readUnsignedShort();
        }catch (IOException e){
            e.printStackTrace();
            log.error("U2 Constructor error e = {}",e.getMessage());
        }
    }

    public U2(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

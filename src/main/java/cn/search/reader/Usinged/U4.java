package cn.search.reader.Usinged;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.IOException;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class U4 extends U {

    private long value;

    public U4(DataInputStream dataInput) {
        try {
            value = dataInput.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("U4 Constructor error e = {}",e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

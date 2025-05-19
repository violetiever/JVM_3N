package cn.search.reader.Usinged;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.IOException;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class U1 extends U {

    private int value;

    public U1(DataInputStream dataInput) {
        try {
            this.value = dataInput.readUnsignedByte();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("U1 Constructor error e = {}", e.getMessage());
        }
    }



}

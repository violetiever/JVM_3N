package cn.search.reader.Utils;

import cn.search.reader.Usinged.U2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonUtil {

    public static int parseBranchByte(int branchbyte1, int branchbyte2) {
        return ((byte) branchbyte1) << 8 | ((byte) branchbyte2);
    }

    public static int parseIndexByte(int indexbyte1, int indexbyte2) {
        return ((indexbyte1 << 8) | indexbyte2) - 1;
    }

    public static String[] resolveAccessFlag(HashMap<Integer, String> accessFlagsMap, U2 accessFlags) {
        List<String> accessFlagsTemp = new ArrayList<>();
        accessFlagsMap.keySet().forEach(k -> {
            if ((accessFlags.getValue() & k) == k)
                accessFlagsTemp.add(accessFlagsMap.get(k));
        });
        String[] result = new String[accessFlagsTemp.size()];
        for (int i = 0; i < accessFlagsTemp.size(); i++) {
            result[i] = accessFlagsTemp.get(i);
        }
        return result;
    }

}

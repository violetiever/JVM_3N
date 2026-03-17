package cn.search.reader.Utils;

public class CommonUtil {

    public static int parseBranchByte(int branchbyte1, int branchbyte2) {
        return ((byte) branchbyte1) << 8 | ((byte) branchbyte2);
    }

    public static int parseIndexByte(int indexbyte1, int indexbyte2) {
        return ((indexbyte1 << 8) | indexbyte2) - 1;
    }

}

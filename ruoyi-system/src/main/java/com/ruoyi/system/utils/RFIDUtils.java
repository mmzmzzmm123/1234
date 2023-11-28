package com.ruoyi.system.utils;

import java.text.SimpleDateFormat;

public class RFIDUtils {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");

    /**
     * @return 生成RFID 88 yyyyMMddhhmmss 000001
     */
    public static String generateRfidId(long order) {
        return "FFFF" + df.format(System.currentTimeMillis()) + convertOrderSuffix(order);
    }

    private static String convertOrderSuffix(long order) {
        String hexString = Long.toHexString(order);
        int hexLength = hexString.length();
        if (hexLength > 6) {
            throw new RuntimeException("order size exceed 0xFFFFFF");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6 - hexLength; i++) {
            sb.append("0");
        }
        return sb.append(hexString).toString();
    }
}

package com.xinyu.idol.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Util {
   private static   MessageDigest digest;;
    static {
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sha1(byte[] byteParam) {

        digest.update(byteParam);
        byte[] hashedBytes = digest.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        String hashedData = hexString.toString();
        return hashedData;

    }

}

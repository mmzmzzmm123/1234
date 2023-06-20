package com.ruoyi.common.utils;

//import sun.misc.BASE64Encoder;
import org.apache.commons.codec.binary.Base64;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TokenProcessor {


    private TokenProcessor() {};

    private static final TokenProcessor instance = new TokenProcessor();


    public static TokenProcessor getInstance() {
        return instance;
    }


    /**
     * 生成Token
     *
     * @return
     */

    public String generateToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(token.getBytes());
//            BASE64Encoder encoder = new BASE64Encoder();
//            return encoder.encode(md5);
            return Base64.encodeBase64String(md5);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

}
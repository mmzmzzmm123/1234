package com.stdiet.common.utils.sign;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES加密工具类
 */
public class AesUtils {

    private static final String KEY_ALGORITHM = "AES";
    private static final int KEYSIZE = 128;                //AES密钥长度
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";  //默认的加密算法
    private static final String DEFAULT_KEY = "Yhokm#876OTG!c";


    public static String encrypt(String content) {
        return encrypt(content, null);
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) {
        key = key == null ? DEFAULT_KEY : key;
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));  // 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);  // 加密

            return Hex.encodeHexString(result);
            //return Base64Utils.encodeToString(result);  //通过Base64转码返回
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static String decrypt(String content) {
        return decrypt(content, null);
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        key = key == null ? DEFAULT_KEY : key;
        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));

            //执行操作
            byte[] result = cipher.doFinal(Hex.decodeHex(content));//Base64Utils.decodeFromString(content)

            return new String(result, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(String key) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            //AES 要求密钥长度为 128
            kg.init(KEYSIZE, random);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("4470"));
    }
}

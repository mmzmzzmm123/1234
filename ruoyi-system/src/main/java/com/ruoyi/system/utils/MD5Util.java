package com.ruoyi.system.utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * MD5加密工具类
 * </p>
 * @author
 * @date 2019-12-05
 */
public class MD5Util {
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * MD5
     *
     * @param b byte数组
     * @return String byte数组处理后字符串
     */
    public static String toHexString(byte[] b) {// String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * MD5运算
     *
     * @param s 传入明文
     * @return String 返回密文
     */
//    public static String MD5String(String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//            return toHexString(messageDigest);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return s;
//    }

    /**
     * MD5运算
     *
     * @param s
     * @return
     */
    public static byte[] MD5Bytes(String s) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
	 /**
     * 先Md5再Base64加密
     * 用于密码加密
     */
    public static String encryptByMd5AndBASE64(String pwd) {
        try {
            byte messageDigest[] = MD5Util.MD5Bytes(pwd);
            return Base64.encode(messageDigest);
        } catch (Exception e) {
            return "";
        }
    }
	
    
    
//    public static void main(String[] args) {
//    	//加密
//		String a = "123123";
//		System.err.println(EncryptUtil.e(a));  //4297f44b13955235245b2497399d7a93
//		byte[] md5Bytes = MD5Util.MD5Bytes(a);
//		System.out.println(md5Bytes);  //[B@15db9742
//		String encode = Base64.encode(md5Bytes);
//		System.out.println(encode);   //Qpf0SxOVUjUkWySXOZ16kw==
//
//		//解密
//		byte[] decode = Base64.decode(encode);
//		System.out.println(decode);   //[B@6d06d69c
//		String hexString = MD5Util.toHexString(decode);
//		System.err.println(hexString);  //4297f44b13955235245b2497399d7a93
//
//
//		System.err.println(MD5Util.encryptByMd5AndBASE64("123123"));
//
//	}
    
    public final static String md5(String paramSrc,String enCode) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = paramSrc.getBytes(enCode);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成md5
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        String base = str;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    /**
     * 生成md5
     * @param dataStr
     * @return
     */
    public static String encrypt(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}

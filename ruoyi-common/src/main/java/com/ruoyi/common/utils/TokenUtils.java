package com.ruoyi.common.utils;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.TokenExpireException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author LAM
 * @date 2023/10/3 11:05
 */
public class TokenUtils {

    /**
     * 自定义的盐值
     */
    private static final String SALT = "1234567890123456";
    private static final String DEFAULT_FLAG = "yingde";

    /**
     * 获取token信息
     * */
    public static String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("authorization");
        boolean isAuth = StringUtils.isNull(token) || DEFAULT_FLAG.equals(token);
        if (isAuth) {
            throw new TokenExpireException("您未登录哟", HttpStatus.WARN_WX);
        }
        return token;
    }

    /**
     * 获取用户标识
     *
     * @return 结果
     */
    public static Long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("authorization");
        boolean isAuth = StringUtils.isNull(token) || DEFAULT_FLAG.equals(token);
        if (isAuth) {
            throw new TokenExpireException("您未登录哟", HttpStatus.WARN_WX);
        }
        String idStr = "0";
        try {
            String temp = decryptAes(token);
            if (ObjectUtil.isNotNull(temp)) {
                idStr = StringUtils.split(temp, ",")[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.valueOf(idStr);
    }

    /**
     * 获取用户openId
     *
     * @return 结果
     * */
    public static String getOpenId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("authorization");
        boolean isAuth = StringUtils.isNull(token) || DEFAULT_FLAG.equals(token);
        if (isAuth) {
            throw new TokenExpireException("您未登录哟", HttpStatus.WARN_WX);
        }
        String openId = "";
        try {
            String temp = decryptAes(token);
            if (ObjectUtil.isNotNull(temp)) {
                openId = StringUtils.split(temp, ",")[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openId;
    }

    /**
     * 使用AES密钥加密数据
     *
     * @param data 加密数据
     * @return 结果
     */
    public static String encryptAes(String data) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, SALT.getBytes(StandardCharsets.UTF_8));
        return aes.encryptHex(String.valueOf(data));
    }

    /**
     * 使用AES密钥解密数据
     *
     * @param encryptedText 加密文本
     * @return 结果
     */
    public static String decryptAes(String encryptedText) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, SALT.getBytes(StandardCharsets.UTF_8));
        return aes.decryptStr(encryptedText, StandardCharsets.UTF_8);
    }
}

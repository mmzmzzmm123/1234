package com.ruoyi.framework.aspectj;

import com.ruoyi.common.annotation.DecryptLogin;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * RSA解密
 *
 * @author wrw
 */
@Aspect
@Component
public class DecryptParameters {

    @Autowired
    private RedisCache redisCache;

    @Before("@annotation(decrypt)")
    public void doBefore(JoinPoint point, DecryptLogin decrypt) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException {
        LoginBody pointArg = (LoginBody) point.getArgs()[0];
        //从缓存里获取私钥
        String loginPrivateKey = redisCache.getCacheObject(Constants.PRE_LOGIN_KEY + pointArg.getPublicKey());
        if (StringUtils.isEmpty(loginPrivateKey)) {
            throw new UserException("RSA密钥对已过期！", point.getArgs());
        }
        //初始化解密密钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(loginPrivateKey));
        //创建密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //解析密钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //解密
        byte[] username = cipher.doFinal(Base64.decodeBase64(pointArg.getUsername()));
        byte[] password = cipher.doFinal(Base64.decodeBase64(pointArg.getPassword()));
        pointArg.setUsername(new String(username));
        pointArg.setPassword(new String(password));
    }
}
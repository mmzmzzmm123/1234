package com.ruoyi.system.utils;

import cn.hutool.core.codec.Base64;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.model.credit.PartnersRsaKey;
import com.ruoyi.system.service.impl.DataCompanyLoanServiceImpl;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 泉州市公共信用信息平台三期API统一授权方法
 */
@Service
public class CreditApiAuthUtil {
    private static final Logger log = LoggerFactory.getLogger(CreditApiAuthUtil.class);

    private static String byteHEX(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }


    /**
     * MD5校验
     * @param buf
     * @return
     */
    public static String encoderByMd5(String buf) {
        try {
            MessageDigest digist = MessageDigest.getInstance("MD5");
            byte[] rs = digist.digest(buf.getBytes("UTF-8"));
            StringBuffer digestHexStr = new StringBuffer();
            for (int i = 0; i < 16; i++) {
                digestHexStr.append(byteHEX(rs[i]));
            }
            return digestHexStr.toString();
        } catch (Exception e) {
            log.error("字符串加密出现异常:{}", e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取token
     * @param rsaKey
     * @return
     */
    public static String getToken(String timespan, PartnersRsaKey rsaKey) {
        if(StringUtils.isEmpty(timespan)) {
            timespan = String.valueOf((System.currentTimeMillis() / 1000));//精确到秒的Unix时间戳
        }
        String toMd5Str = rsaKey.getPlatformPrivateKey().concat(timespan).concat(rsaKey.getPlatformPublicKey());
        String myToken;
        if(StringUtils.isEmpty(rsaKey.getSalt())) {
            myToken = encoderByMd5(toMd5Str);
        } else {
            myToken = encoderByMd5(encoderByMd5(toMd5Str) + rsaKey.getSalt());
        }
        return myToken;
    }


    /**
     * 获取请求头信息
     * @param rsaKey
     * @return
     */
    public static Map<String, String> getHeadData(PartnersRsaKey rsaKey){
        String timestamp = String.valueOf((System.currentTimeMillis()/1000));//精确到秒的Unix时间戳
        String token = getToken(timestamp, rsaKey);    //验证加密值
        Map<String, String> resultMap = new HashMap<>(4);
        //【当前最新时间】timespan有效期是五分钟
        resultMap.put("Timespan", timestamp);
        resultMap.put("Token", token);
        resultMap.put("MerchantId", rsaKey.getMerchantId());
        return resultMap;
    }

}

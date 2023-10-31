package com.ruoyi.office.ttlock;

import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.horn.HornSendMsg;
import com.ruoyi.office.horn.HornSendMsgData;
import com.ruoyi.office.horn.HornSendMsgDataInfo;
import com.ruoyi.office.util.WxMsgSender;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TtlockConfig {

    protected static final Logger logger = LoggerFactory.getLogger(TtlockConfig.class);

    public static String clientId;
    public static String clientSecret;
    public static String url;
    public static String minutes;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);

    @Autowired
    ISysDictDataService dictDataService;

    @PostConstruct
    public void intTtlockConfig() {
        SysDictData dictData = new SysDictData();
        dictData.setDictType("ttlock");
        final Map<String, String> ttlockConfig = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));

        clientId = ttlockConfig.get("client_id");
        clientSecret = ttlockConfig.get("client_secret");
        url = ttlockConfig.get("url");
        minutes = ttlockConfig.get("minute");
    }

    public static TtlockTokenRes getToken(String username, String password) {
        TtlockTokenReq ttlockTokenReq = new TtlockTokenReq();
        ttlockTokenReq.setClientId(TtlockConfig.clientId);
        ttlockTokenReq.setClientSecret(TtlockConfig.clientSecret);
        ttlockTokenReq.setUsername(username);
        ttlockTokenReq.setPassword(password);
        String contentType = "application/x-www-form-urlencoded";
        String response = HttpUtils.sendPostByType(TtlockConfig.url + "/oauth2/token", toFormUrlEncodedString(ttlockTokenReq), contentType);
        TtlockTokenRes res = JSONObject.parseObject(response, TtlockTokenRes.class);
        return res;
    }

    public static TtlockGatewayRes unlock(String accessToken, String lockId) {
        TtlockGatewayReq ttlockGatewayReq = new TtlockGatewayReq();
        ttlockGatewayReq.setClientId(TtlockConfig.clientId);
        ttlockGatewayReq.setAccessToken(accessToken);
        ttlockGatewayReq.setLockId(lockId);
        ttlockGatewayReq.setDate(Long.toString(System.currentTimeMillis()));
        String contentType = "application/x-www-form-urlencoded";
        String response = HttpUtils.sendPostByType(TtlockConfig.url + "/v3/lock/unlock", toFormUrlEncodedString(ttlockGatewayReq), contentType);

        TtlockGatewayRes res = JSONObject.parseObject(response, TtlockGatewayRes.class);
        if ("0".equalsIgnoreCase(res.getErrcode())) {
            return res;
        } else {
            logger.error("通通锁TTLOCK开锁失败：" + res.getErrcode() + res.getErrmsg() + res.getDescription());
            throw new ServiceException("锁不在线或网络信号弱");
        }
    }

    public static TtlockGatewayRes lock(String accessToken, String lockId) {
        TtlockGatewayReq ttlockGatewayReq = new TtlockGatewayReq();
        ttlockGatewayReq.setClientId(TtlockConfig.clientId);
        ttlockGatewayReq.setAccessToken(accessToken);
        ttlockGatewayReq.setLockId(lockId);
        ttlockGatewayReq.setDate(Long.toString(System.currentTimeMillis()));
        String contentType = "application/x-www-form-urlencoded";
        String response = HttpUtils.sendPostByType(TtlockConfig.url + "/v3/lock/lock", toFormUrlEncodedString(ttlockGatewayReq), contentType);
        TtlockGatewayRes res = JSONObject.parseObject(response, TtlockGatewayRes.class);
        if ("0".equalsIgnoreCase(res.getErrcode())) {
            return res;
        } else {
            logger.error("通通锁TTLOCK关锁失败：" + res.getErrcode() + res.getErrmsg() + res.getDescription());
            throw new ServiceException("锁不在线或网络信号弱");
        }
    }

    public static TtlockTokenRes getTokenTest(String username, String password) {
        TtlockConfig.clientId = "23ab43d84bde4e6e9e1781752062a976";
        TtlockConfig.clientSecret = "4ad320f12bcebe8ece923586f51b9f96";
        TtlockConfig.url = "https://cnapi.ttlock.com";
        TtlockTokenReq ttlockTokenReq = new TtlockTokenReq();
        ttlockTokenReq.setClientId(TtlockConfig.clientId);
        ttlockTokenReq.setClientSecret(TtlockConfig.clientSecret);
        ttlockTokenReq.setUsername(username);
        ttlockTokenReq.setPassword(password);
        String contentType = "application/x-www-form-urlencoded";
        String response = HttpUtils.sendPostByType(TtlockConfig.url + "/oauth2/token", toFormUrlEncodedString(ttlockTokenReq), contentType);
        TtlockTokenRes res = JSONObject.parseObject(response, TtlockTokenRes.class);
        return res;
    }

    public static TtlockGatewayRes unlockTest(String accessToken, String lockId) {
        TtlockConfig.clientId = "23ab43d84bde4e6e9e1781752062a976";
        TtlockConfig.clientSecret = "4ad320f12bcebe8ece923586f51b9f96";
        TtlockConfig.url = "https://cnapi.ttlock.com";
        TtlockGatewayReq ttlockGatewayReq = new TtlockGatewayReq();
        ttlockGatewayReq.setClientId(TtlockConfig.clientId);
        ttlockGatewayReq.setAccessToken(accessToken);
        ttlockGatewayReq.setLockId(lockId);
        ttlockGatewayReq.setDate(Long.toString(System.currentTimeMillis()));
        String contentType = "application/x-www-form-urlencoded";
        String response = HttpUtils.sendPostByType(TtlockConfig.url + "/v3/lock/unlock", toFormUrlEncodedString(ttlockGatewayReq), contentType);

        TtlockGatewayRes res = JSONObject.parseObject(response, TtlockGatewayRes.class);
        if ("0".equalsIgnoreCase(res.getErrcode())) {
            return res;
        } else {
            throw new ServiceException("通通锁开锁失败:" + res.getErrmsg());
        }
    }

    public static TtlockGatewayRes lockTest(String accessToken, String lockId) {
        TtlockConfig.clientId = "23ab43d84bde4e6e9e1781752062a976";
        TtlockConfig.clientSecret = "4ad320f12bcebe8ece923586f51b9f96";
        TtlockConfig.url = "https://cnapi.ttlock.com";
        TtlockGatewayReq ttlockGatewayReq = new TtlockGatewayReq();
        ttlockGatewayReq.setClientId(TtlockConfig.clientId);
        ttlockGatewayReq.setAccessToken(accessToken);
        ttlockGatewayReq.setLockId(lockId);
        ttlockGatewayReq.setDate(Long.toString(System.currentTimeMillis()));
        String contentType = "application/x-www-form-urlencoded";
        String response = HttpUtils.sendPostByType(TtlockConfig.url + "/v3/lock/lock", toFormUrlEncodedString(ttlockGatewayReq), contentType);
        TtlockGatewayRes res = JSONObject.parseObject(response, TtlockGatewayRes.class);
        if ("0".equalsIgnoreCase(res.getErrcode())) {
            return res;
        } else {
            throw new ServiceException("通通锁关锁失败:" + res.getErrmsg());
        }
    }

    private static String toFormUrlEncodedString(Object object) {
        JavaBeanSerializer writer = (JavaBeanSerializer) SerializeConfig.getGlobalInstance().getObjectWriter(object.getClass());
        try {
            Map<String, Object> map = writer.getFieldValuesMap(object);
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> nameAndValue :
                    map.entrySet()) {
                Object val = nameAndValue.getValue();
                if (val != null) {
                    if (val instanceof Date) {
                        val = dateFormat.format(val);
                    }
                    sb.append(nameAndValue.getKey()).append('=').append(URLEncoder.encode(val.toString(), "UTF-8")).append('&');
                }
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        } catch (Exception e) {
            throw new ServiceException("接口参数序列化出错");
        }
    }
}

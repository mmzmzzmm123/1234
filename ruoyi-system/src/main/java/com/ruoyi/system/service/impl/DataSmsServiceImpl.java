package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.mzt.HttpRequest;
import com.ruoyi.common.utils.mzt.MD5;
import com.ruoyi.system.domain.model.DataCodeMsgResponse;
import com.ruoyi.system.service.IDataSmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 短信接口服务。对接文档请参考：http://47.97.21.146:9090/doc/sms#codemsg
 *
 * @author genius
 * @date 2021-07-13
 */
@Service
public class DataSmsServiceImpl implements IDataSmsService {
    /**
     * 行业短信域名
     */
    @Value("${hysms.domain}")
    private String domain;

    /**
     * 行业短信账号标识
     */
    @Value("${hysms.mchId}")
    private String mchId;

    /**
     * 行业短信接口秘钥
     */
    @Value("${hysms.apiKey}")
    private String apiKey;

    /**
     * 行业短信回调通知地址
     */
    @Value("${hysms.notifyUrl}")
    private String notifyUrl;

    private static final String TEP_ID = "138";
    private static final String TGA_ID = "134";

    private String sendCodeSms(String mobile, String tmpId, String tgaId, List<String> list) {

        String url = domain + "sms/Service/CodeMsg";
        long timestamp = System.currentTimeMillis();
        MD5 md5 = new MD5();
        String password = mchId + "&" + timestamp + "&" + apiKey;
        String sign = md5.getMD5ofStr(password).toUpperCase();

        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_id", mchId);
        map.put("contents", list.toString());
        map.put("tmp_id", tmpId);
        map.put("phone_num", mobile);
        map.put("time_stamp", timestamp + "");
        map.put("sign", sign);
        map.put("notify_url", notifyUrl);
        map.put("tga_id", tgaId);

        System.out.println(map);
        // TODO:httpclient换一个好一点的;日志如何打印？
        String result = HttpRequest.sendPost(url, map);
        System.out.println(result);
        return result;

    }

    @Override
    public DataCodeMsgResponse sendVerifyCode(String phone, String code) {
        //TODO:此短信模板两个参数只允许为数字？
        String param1 = "1";
        List<String> list = new ArrayList<>();
        list.add(param1);
        list.add(code);
        String result = this.sendCodeSms(phone, TEP_ID, TGA_ID, list);
        return JSON.parseObject(result, DataCodeMsgResponse.class);
    }
}

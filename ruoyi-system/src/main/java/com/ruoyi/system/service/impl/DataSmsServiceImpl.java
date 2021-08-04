package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.system.domain.model.DataCodeMsgResponse;
import com.ruoyi.system.service.IDataSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(DataSmsServiceImpl.class);

    private static final String TEP_ID = "138";
    private static final String TGA_ID = "134";

    private DataCodeMsgResponse sendCodeSms(String mobile, String tmpId, String tgaId, List<String> list) {

        String url = domain + "sms/Service/CodeMsg";
        long timestamp = System.currentTimeMillis();
        String password = mchId + "&" + timestamp + "&" + apiKey;
        String sign = Md5Utils.hash(password).toUpperCase();

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("mch_id", mchId);
        map.add("contents", list.toString());
        map.add("tmp_id", tmpId);
        map.add("phone_num", mobile);
        map.add("time_stamp", timestamp + "");
        map.add("sign", sign);
        map.add("notify_url", notifyUrl);
        map.add("tga_id", tgaId);

        log.info("短信参数:" + map.toString());
        DataCodeMsgResponse response = restTemplate.postForObject(url, map, DataCodeMsgResponse.class);
        log.info("短信响应:" + (response != null ? response.toString() : null));
        return response;
    }

    @Override
    public DataCodeMsgResponse sendVerifyCode(String phone, String code) {
        String param1 = "1";
        List<String> list = new ArrayList<>();
        list.add(param1);
        list.add(code);
        return this.sendCodeSms(phone, TEP_ID, TGA_ID, list);
    }

    @Override
    public AjaxResult sendVerifyCodeByUMS(String phone, String code) {
        String url = "https://api.ums86.com:9600/sms/Api/Send.do";

        long timestamp = System.currentTimeMillis();
        String spCode = "000001";
        String loginName = "admin";
        String password = "admin";
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("SpCode", spCode);
        map.add("LoginName", loginName);
        map.add("Password", password);
        map.add("MessageContent", "你有一项编号为"+code+"的事务需要处理");
        map.add("UserNumber", phone);
        map.add("SerialNumber", timestamp);
        map.add("ScheduleTime", "");
        map.add("f", 1);

        //格式为：result=2&description=账号无效或权限不足
        String result = restTemplate.postForObject(url, map, String.class);


        String[] results = result.split("&");
        String resultCode = results[0].split("=")[1];
        String resultMsg = results[1].split("=")[1];
        AjaxResult ajaxResult = new AjaxResult(Integer.parseInt(resultCode),resultMsg);

        return ajaxResult;
    }
}

package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.system.domain.model.DataCodeMsgResponse;
import com.ruoyi.system.service.IDataSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
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

        // response转码为 GBK，否则返回值出现乱码
        restTemplate.getMessageConverters().add(1, new StringHttpMessageConverter(Charset.forName("GBK")));

        String spCode = "264597";
        String loginName = "qz_dsjg";
        String password = "e363da464f92ecb750cc50576c9757d8";
        long timestamp = System.currentTimeMillis();

        String content = "尊敬的信贷直通车用户，您本次验证码为" + code + "，请在2分钟内使用。";

        String url = "https://api.ums86.com:9600/sms/Api/Send.do";
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
        urlBuilder.queryParam("SpCode", spCode);
        urlBuilder.queryParam("LoginName", loginName);
        urlBuilder.queryParam("Password", password);
        urlBuilder.queryParam("MessageContent", content);
        urlBuilder.queryParam("UserNumber", phone);
        urlBuilder.queryParam("SerialNumber", timestamp+"");
        urlBuilder.queryParam("ScheduleTime", "");
        urlBuilder.queryParam("f", "1");

        // request 编码为 GBK，否则短信服务接收参数会出现乱码
        URI gbkUri = urlBuilder.build().encode(Charset.forName("GBK")).toUri();

        String result = restTemplate.getForEntity(gbkUri,String.class).toString();

        //格式为：result=2&description=账号无效或权限不足
        String[] results = result.split("&");
        String resultCode = results[0].split("=")[1];
        String resultMsg = results[1].split("=")[1];

        return new AjaxResult(Integer.parseInt(resultCode),resultMsg);
    }
}

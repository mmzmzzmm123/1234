package com.ruoyi.office.horn;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.office.domain.vo.CloudHornRegResponse;
import com.ruoyi.office.mqtt.MqttCondition;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.system.service.ISysDictDataService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class HornConfig {

    public static String appId;
    public static String appSecret;
    public static String url;
    public static String minutes;
    public static String volume;

    @Autowired
    ISysDictDataService dictDataService;

    @PostConstruct
    public void intHornConfig() {
        HornConfig sender = new HornConfig();
        SysDictData dictData = new SysDictData();
        dictData.setDictType("horn");
        final Map<String, String> hornConfig = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));

        appId = hornConfig.get("app_id");
        appSecret = hornConfig.get("app_secret");
        url = hornConfig.get("url");
        minutes = hornConfig.get("minute");
        volume = hornConfig.get("volume");
    }

    public static CloudHornRegResponse regHorn(String hornSn) {
        Map<String, String> param = new HashMap<>();
        param.put("app_id", HornConfig.appId);
        param.put("app_secret", HornConfig.appSecret);
        param.put("device_sn", hornSn);
        String response = HttpUtils.sendPost(HornConfig.url + "/register", JSONObject.toJSONString(param));
        CloudHornRegResponse resp = JSONObject.parseObject(response, CloudHornRegResponse.class);
        if ("0".equalsIgnoreCase(resp.getCode())) {
            return resp;
        } else {
            throw new ServiceException("云喇叭注册失败:" + resp.getMsg());
        }
    }

    public static CloudHornRegResponse hornSend(String hornSn, String msg) {
        return HornConfig.hornSend(hornSn, msg, 10);
    }

    public static CloudHornRegResponse hornSend(String hornSn, String msg, int inner) {

        HornSendMsg hornMsg = new HornSendMsg();
        hornMsg.setAppId(HornConfig.appId);
        hornMsg.setAppSecret(HornConfig.appSecret);
        hornMsg.setDeviceSn(hornSn);
//        hornMsg.setType(1);

        HornSendMsgData hornData = new HornSendMsgData();
        hornData.setCmdType("play");

        HornSendMsgDataInfo hornDataInfo = new HornSendMsgDataInfo();
        hornDataInfo.setInner(inner);
        hornDataInfo.setTts(msg);
        hornDataInfo.setVolume(Integer.parseInt(HornConfig.volume));

        hornData.setInfo(hornDataInfo);
        hornMsg.setData(hornData);

        String response = HttpUtils.sendPost(HornConfig.url + "/send", JSONObject.toJSONString(hornMsg));
        CloudHornRegResponse resp = JSONObject.parseObject(response, CloudHornRegResponse.class);

        return resp;
    }


}

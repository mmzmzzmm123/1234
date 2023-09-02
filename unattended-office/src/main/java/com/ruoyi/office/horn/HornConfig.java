package com.ruoyi.office.horn;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.http.HttpUtils;
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
    }
}

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

@Data
@Configuration
public class HornConfig {

    private String appId;
    private String appSecret;
    private String url;
    private String minutes;

    @Autowired
    ISysDictDataService dictDataService;

    @PostConstruct
    public HornConfig intHornConfig() {
        HornConfig sender = new HornConfig();
        SysDictData dictData = new SysDictData();
        dictData.setDictType("horn");
        final Map<String, String> hornConfig = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));

        sender.setAppId(hornConfig.get("app_id"));
        sender.setAppSecret(hornConfig.get("app_secret"));
        sender.setUrl(hornConfig.get("url"));
        sender.setMinutes(hornConfig.get("minute"));
        return sender;
    }
}

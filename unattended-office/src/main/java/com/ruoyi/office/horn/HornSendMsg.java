package com.ruoyi.office.horn;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HornSendMsg {

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("app_secret")
    private String appSecret;

    @JsonProperty("device_sn")
    private String deviceSn;

    private Integer type;

    private HornSendMsgData data;
}

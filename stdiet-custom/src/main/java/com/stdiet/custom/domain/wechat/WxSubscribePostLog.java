package com.stdiet.custom.domain.wechat;

import com.google.gson.JsonObject;
import lombok.Data;

import java.util.Date;

@Data
public class WxSubscribePostLog {
    Long id;
    String appid;
    String openid;
    Long planId;
    Date sendTime;
    Integer errcode;
    String errmsg;
    JsonObject data;
}

package com.stdiet.custom.domain.wechat;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

@Data
public class WxSubscribePostLog {
    Long id;
    String appid;
    String openid;
    String phone;
    Long planId;
    Date sendTime;
    JSONObject result;
    JSONObject data;
    Integer type;
}

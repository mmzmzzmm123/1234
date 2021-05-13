package com.stdiet.custom.service;

public interface IWechatAppletService {

    public String getAccessToken(String appId);

    public void postRecipesMessage(String appId, String openId, String name, String startDate, String endDate, String remark);
}

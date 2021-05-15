package com.stdiet.custom.service;

public interface IWechatAppletService {

    public String getAccessToken(String appId);

    public String postRecipesMessage(Long cusId, Long planId, String name, String startDate, String endDate, String remark);
}

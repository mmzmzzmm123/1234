package com.stdiet.custom.service;

public interface IWechatAppletService {

    public String getAccessToken(String appId);

    public Integer postSubscribeMessage(Long cusId, Long planId, String name, String startDate, String endDate, String remark);

    public Integer postSms(Long cusId, Long planId, String plan);

    public Integer postCustomerPunchNotice(String name, String toUser);
}

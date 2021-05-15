package com.stdiet.custom.service;

import com.stdiet.custom.domain.wechat.WxSubscribePostLog;

import java.util.List;

public interface IWxSubscribePostLogService {
    public int insertWxSubscribePostLog(WxSubscribePostLog postLog);

    public List<WxSubscribePostLog> selectWxSubscribePostLog(WxSubscribePostLog postLog);
}

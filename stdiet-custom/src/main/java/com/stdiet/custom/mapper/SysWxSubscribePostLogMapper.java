package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.wechat.WxSubscribePostLog;

import java.util.List;

public interface SysWxSubscribePostLogMapper {
    public int insertWxSubscribePostLog(WxSubscribePostLog postLog);

    public List<WxSubscribePostLog> selectWxSubscribePostLog(WxSubscribePostLog postLog);
}

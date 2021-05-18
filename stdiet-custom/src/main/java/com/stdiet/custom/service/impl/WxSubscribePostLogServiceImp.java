package com.stdiet.custom.service.impl;

import com.stdiet.custom.domain.wechat.WxSubscribePostLog;
import com.stdiet.custom.mapper.SysWxSubscribePostLogMapper;
import com.stdiet.custom.service.IWxSubscribePostLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxSubscribePostLogServiceImp implements IWxSubscribePostLogService {

    @Autowired
    SysWxSubscribePostLogMapper sysWxSubscribePostLogMapper;

    @Override
    public int insertWxSubscribePostLog(WxSubscribePostLog postLog) {
        return sysWxSubscribePostLogMapper.insertWxSubscribePostLog(postLog);
    }

    @Override
    public List<WxSubscribePostLog> selectWxSubscribePostLog(WxSubscribePostLog postLog) {
        return sysWxSubscribePostLogMapper.selectWxSubscribePostLog(postLog);
    }
}

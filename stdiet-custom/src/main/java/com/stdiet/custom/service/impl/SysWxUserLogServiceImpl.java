package com.stdiet.custom.service.impl;

import java.util.List;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.page.WxLogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxUserLogMapper;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.service.ISysWxUserLogService;

/**
 * 微信用户记录Service业务层处理
 *
 * @author wonder
 * @date 2020-11-28
 */
@Service
public class SysWxUserLogServiceImpl implements ISysWxUserLogService {
    @Autowired
    private SysWxUserLogMapper sysWxUserLogMapper;

    /**
     * 查询微信用户记录
     *
     * @param id 微信用户记录ID
     * @return 微信用户记录
     */
    @Override
    public SysWxUserLog selectSysWxUserLogById(String id) {
        return sysWxUserLogMapper.selectSysWxUserLogById(id);
    }

    /**
     * 查询微信用户记录列表
     *
     * @param sysWxUserLog 微信用户记录
     * @return 微信用户记录
     */
    @Override
    public List<SysWxUserLog> selectSysWxUserLogList(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.selectSysWxUserLogList(sysWxUserLog);
    }

    @Override
    public List<WxLogInfo> selectWxLogInfoList(SysWxUserLog sysWxUserLog) {
        return  sysWxUserLogMapper.selectWxLogInfoList(sysWxUserLog);
    }

    /**
     * 新增微信用户记录
     *
     * @param sysWxUserLog 微信用户记录
     * @return 结果
     */
    @Override
    public int insertSysWxUserLog(SysWxUserLog sysWxUserLog) {
        sysWxUserLog.setCreateTime(DateUtils.getNowDate());
        return sysWxUserLogMapper.insertSysWxUserLog(sysWxUserLog);
    }

    /**
     * 修改微信用户记录
     *
     * @param sysWxUserLog 微信用户记录
     * @return 结果
     */
    @Override
    public int updateSysWxUserLog(SysWxUserLog sysWxUserLog) {
        sysWxUserLog.setUpdateTime(DateUtils.getNowDate());
        return sysWxUserLogMapper.updateSysWxUserLog(sysWxUserLog);
    }

    /**
     * 批量删除微信用户记录
     *
     * @param ids 需要删除的微信用户记录ID
     * @return 结果
     */
    @Override
    public int deleteSysWxUserLogByIds(String[] ids) {
        return sysWxUserLogMapper.deleteSysWxUserLogByIds(ids);
    }

    /**
     * 删除微信用户记录信息
     *
     * @param id 微信用户记录ID
     * @return 结果
     */
    @Override
    public int deleteSysWxUserLogById(Long id) {
        return sysWxUserLogMapper.deleteSysWxUserLogById(id);
    }

    @Override
    public int checkWxLogInfoCount(String openid) {
        return sysWxUserLogMapper.checkWxLogInfoCount(openid);
    }

    /**
     * 根据openid和手机号查询打卡记录
     * @return
     */
    @Override
    public List<WxLogInfo> getWxLogInfoList(SysWxUserLog sysWxUserLog){
        return sysWxUserLogMapper.getWxLogInfoList(sysWxUserLog);
    }

    /**
     * 根据日期和openid查询打卡记录
     * @param sysWxUserLog
     * @return
     */
    public SysWxUserLog selectSysWxUserLogByDateAndOpenId(SysWxUserLog sysWxUserLog){
        return sysWxUserLogMapper.selectSysWxUserLogByDateAndOpenId(sysWxUserLog);
    }

}
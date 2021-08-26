package com.stdiet.custom.service.impl;

import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.dto.response.CommunityPunchReponse;
import com.stdiet.custom.mapper.SysWxUserLogMapper;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.ISysWxUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return sysWxUserLogMapper.selectWxLogInfoList(sysWxUserLog);
    }

    /**
     * 新增微信用户记录
     *
     * @param sysWxUserLog 微信用户记录
     * @return 结果
     */
    @Override
    public int insertSysWxUserLog(SysWxUserLog sysWxUserLog) {
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
    public int checkWxLogInfoCount(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.checkWxLogInfoCount(sysWxUserLog);
    }

    /**
     * 根据openid和手机号查询打卡记录
     *
     * @return
     */
    @Override
    public List<WxLogInfo> getWxLogInfoList(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.getWxLogInfoList(sysWxUserLog);
    }

    /**
     * 根据日期和openid查询打卡记录
     *
     * @param sysWxUserLog
     * @return
     */
    public SysWxUserLog selectSysWxUserLogByDateAndOpenId(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.selectSysWxUserLogByDateAndOpenId(sysWxUserLog);
    }

    /**
     * 根据客户ID查询对应打卡体重数据
     *
     * @param sysWxUserLog
     * @return
     */
    public List<SysWxUserLog> getWxUserLogListByCustomerId(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.getWxUserLogListByCustomerId(sysWxUserLog);
    }

    /**
     * 根据ID查询打卡详情
     *
     * @param sysWxUserLog
     * @return
     */
    public WxLogInfo getWxLogInfoDetailById(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.getWxLogInfoDetailById(sysWxUserLog);
    }

    /**
     * 查询打卡社区记录
     *
     * @param sysWxUserLog
     * @return
     */
    public List<CommunityPunchReponse> getCommunityPunch(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.getCommunityPunch(sysWxUserLog);
    }

    public List<CommunityPunchReponse> getCommunityPunchByOpenid(String openid) {
        return sysWxUserLogMapper.getCommunityPunchByOpenid(openid);
    }


    /**
     * 根据打卡社区
     *
     * @param sysWxUserLog
     * @return
     */
    public int getPunchTotalNum(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.getPunchTotalNum(sysWxUserLog);
    }

    /**
     * 查询打卡社区总共打卡人数
     *
     * @return
     */
    public int getPunchCustomerTotalNum() {
        return sysWxUserLogMapper.getPunchCustomerTotalNum();
    }

    /**
     * 根据日期、营养师、助理、售后查询指定日期未打卡的客户信息
     *
     * @return
     */
    @Override
    public List<SysCustomer> selectNotPunchCustomerByDate(SysCustomer sysCustomer) {
        return sysWxUserLogMapper.selectNotPunchCustomerByDate(sysCustomer);
    }

    /**
     * 查询打卡详情（下一条、上一条）
     * @param sysWxUserLog
     * @return
     */
    public SysWxUserLog getPunchLogDetail(SysWxUserLog sysWxUserLog){
        return sysWxUserLogMapper.getPunchLogDetail(sysWxUserLog);
    }

}
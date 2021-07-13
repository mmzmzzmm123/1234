package com.stdiet.custom.service.impl;

import java.util.List;

import com.stdiet.common.config.AliyunOSSConfig;
import com.stdiet.common.exception.file.FileNameLengthLimitExceededException;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.file.FileUploadUtils;
import com.stdiet.common.utils.file.MimeTypeUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.custom.domain.SysMessageNotice;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.entityEnum.MessageNoticeEnum;
import com.stdiet.custom.dto.response.CommunityPunchReponse;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.ISysMessageNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxUserLogMapper;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.service.ISysWxUserLogService;
import org.springframework.web.multipart.MultipartFile;

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
    public int checkWxLogInfoCount(SysWxUserLog sysWxUserLog) {
        return sysWxUserLogMapper.checkWxLogInfoCount(sysWxUserLog);
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

    /**
     * 根据客户ID查询对应打卡体重数据
     * @param sysWxUserLog
     * @return
     */
    public List<SysWxUserLog> getWxUserLogListByCustomerId(SysWxUserLog sysWxUserLog){
        return sysWxUserLogMapper.getWxUserLogListByCustomerId(sysWxUserLog);
    }

    /**
     * 根据ID查询打卡详情
     * @param sysWxUserLog
     * @return
     */
    public WxLogInfo getWxLogInfoDetailById(SysWxUserLog sysWxUserLog){
        return sysWxUserLogMapper.getWxLogInfoDetailById(sysWxUserLog);
    }

    /**
     * 查询打卡社区记录
     * @param sysWxUserLog
     * @return
     */
    public List<CommunityPunchReponse> getCommunityPunch(SysWxUserLog sysWxUserLog){
        return sysWxUserLogMapper.getCommunityPunch(sysWxUserLog);
    }

    /**
     * 根据打卡社区
     * @param sysWxUserLog
     * @return
     */
    public int getPunchTotalNum(SysWxUserLog sysWxUserLog){
        return  sysWxUserLogMapper.getPunchTotalNum(sysWxUserLog);
    }

    /**
     * 查询打卡社区总共打卡人数
     * @return
     */
    public int getPunchCustomerTotalNum(){
        return  sysWxUserLogMapper.getPunchCustomerTotalNum();
    }

}
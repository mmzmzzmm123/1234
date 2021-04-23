package com.stdiet.custom.mapper;

import java.util.List;

import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.page.WxLogInfo;

/**
 * 微信用户记录Mapper接口
 *
 * @author wonder
 * @date 2020-11-28
 */
public interface SysWxUserLogMapper
{
    /**
     * 查询微信用户记录
     *
     * @param openid 微信用户记录ID
     * @return 微信用户记录
     */
    public SysWxUserLog selectSysWxUserLogById(String openid);

    /**
     * 查询微信用户记录列表
     *
     * @param sysWxUserLog 微信用户记录
     * @return 微信用户记录集合
     */
    public List<SysWxUserLog> selectSysWxUserLogList(SysWxUserLog sysWxUserLog);

    public List<WxLogInfo> selectWxLogInfoList(SysWxUserLog sysWxUserLog);

    /**
     * 新增微信用户记录
     *
     * @param sysWxUserLog 微信用户记录
     * @return 结果
     */
    public int insertSysWxUserLog(SysWxUserLog sysWxUserLog);

    /**
     * 修改微信用户打卡记录
     *
     * @param sysWxUserLog 微信用户记录
     * @return 结果
     */
    public int updateSysWxUserLog(SysWxUserLog sysWxUserLog);

    /**
     * 根据ID删除打卡记录
     * @param id
     * @return
     */
    public int deleteSysWxUserLogById(Long id);

    /**
     * 批量删除微信用户打卡记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWxUserLogByIds(String[] ids);

    public int checkWxLogInfoCount(SysWxUserLog sysWxUserLog);

    /**
     * 根据openid和手机号查询打卡记录
     * @return
     */
    public List<WxLogInfo> getWxLogInfoList(SysWxUserLog sysWxUserLog);

    /**
     * 根据日期和openid查询打卡记录
     * @param sysWxUserLog
     * @return
     */
    SysWxUserLog selectSysWxUserLogByDateAndOpenId(SysWxUserLog sysWxUserLog);

    /**
     * 根据客户ID查询对应打卡体重数据
     * @param sysWxUserLog
     * @return
     */
    List<SysWxUserLog> getWxUserLogListByCustomerId(SysWxUserLog sysWxUserLog);

    /**
     * 根据ID查询打卡详情
     * @param sysWxUserLog
     * @return
     */
    WxLogInfo getWxLogInfoDetailById(SysWxUserLog sysWxUserLog);

}
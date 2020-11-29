package com.stdiet.custom.mapper;

import java.util.List;
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
     * 修改微信用户记录
     *
     * @param sysWxUserLog 微信用户记录
     * @return 结果
     */
    public int updateSysWxUserLog(SysWxUserLog sysWxUserLog);

    /**
     * 删除微信用户记录
     *
     * @param openid 微信用户记录ID
     * @return 结果
     */
    public int deleteSysWxUserLogById(String openid);

    /**
     * 批量删除微信用户记录
     *
     * @param openids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWxUserLogByIds(String[] openids);
}
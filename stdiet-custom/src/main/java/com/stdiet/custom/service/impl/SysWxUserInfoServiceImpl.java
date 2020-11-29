package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxUserInfoMapper;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.service.ISysWxUserInfoService;

/**
 * 微信用户Service业务层处理
 *
 * @author wonder
 * @date 2020-11-28
 */
@Service
public class SysWxUserInfoServiceImpl implements ISysWxUserInfoService
{
    @Autowired
    private SysWxUserInfoMapper sysWxUserInfoMapper;

    /**
     * 查询微信用户
     *
     * @param openid 微信用户ID
     * @return 微信用户
     */
    @Override
    public SysWxUserInfo selectSysWxUserInfoById(String openid)
    {
        return sysWxUserInfoMapper.selectSysWxUserInfoById(openid);
    }

    /**
     * 查询微信用户列表
     *
     * @param sysWxUserInfo 微信用户
     * @return 微信用户
     */
    @Override
    public List<SysWxUserInfo> selectSysWxUserInfoList(SysWxUserInfo sysWxUserInfo)
    {
        return sysWxUserInfoMapper.selectSysWxUserInfoList(sysWxUserInfo);
    }

    /**
     * 新增微信用户
     *
     * @param sysWxUserInfo 微信用户
     * @return 结果
     */
    @Override
    public int insertSysWxUserInfo(SysWxUserInfo sysWxUserInfo)
    {
        sysWxUserInfo.setCreateTime(DateUtils.getNowDate());
        return sysWxUserInfoMapper.insertSysWxUserInfo(sysWxUserInfo);
    }

    /**
     * 修改微信用户
     *
     * @param sysWxUserInfo 微信用户
     * @return 结果
     */
    @Override
    public int updateSysWxUserInfo(SysWxUserInfo sysWxUserInfo)
    {
        sysWxUserInfo.setUpdateTime(DateUtils.getNowDate());
        return sysWxUserInfoMapper.updateSysWxUserInfo(sysWxUserInfo);
    }

    /**
     * 批量删除微信用户
     *
     * @param openids 需要删除的微信用户ID
     * @return 结果
     */
    @Override
    public int deleteSysWxUserInfoByIds(String[] openids)
    {
        return sysWxUserInfoMapper.deleteSysWxUserInfoByIds(openids);
    }

    /**
     * 删除微信用户信息
     *
     * @param openid 微信用户ID
     * @return 结果
     */
    @Override
    public int deleteSysWxUserInfoById(String openid)
    {
        return sysWxUserInfoMapper.deleteSysWxUserInfoById(openid);
    }
}
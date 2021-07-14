package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.mapper.SysWxUserInfoMapper;
import com.stdiet.custom.service.ISysWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信用户Service业务层处理
 *
 * @author wonder
 * @date 2020-11-28
 */
@Service
public class SysWxUserInfoServiceImpl implements ISysWxUserInfoService {
    @Autowired
    private SysWxUserInfoMapper sysWxUserInfoMapper;

    /**
     * 查询微信用户
     *
     * @param openid 微信用户ID
     * @return 微信用户
     */
    @Override
    public SysWxUserInfo selectSysWxUserInfoById(String openid) {
        return sysWxUserInfoMapper.selectSysWxUserInfoById(openid);
    }

    @Override
    public SysWxUserInfo selectSysWxUserInfoByCusId(Long cusId) {
        return sysWxUserInfoMapper.selectSysWxUserInfoByCusId(cusId);
    }

    /**
     * 查询微信用户列表
     *
     * @param sysWxUserInfo 微信用户
     * @return 微信用户
     */
    @Override
    public List<SysWxUserInfo> selectSysWxUserInfoList(SysWxUserInfo sysWxUserInfo) {
        return sysWxUserInfoMapper.selectSysWxUserInfoList(sysWxUserInfo);
    }

    @Override
    public List<SysWxUserInfo> selectSysWxUserInfoListNot(SysWxUserInfo sysWxUserInfo) {
        return sysWxUserInfoMapper.selectSysWxUserInfoListNot(sysWxUserInfo);
    }

    /**
     * 新增微信用户
     *
     * @param sysWxUserInfo 微信用户
     * @return 结果
     */
    @Override
    public int insertSysWxUserInfo(SysWxUserInfo sysWxUserInfo) {
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
    public int updateSysWxUserInfo(SysWxUserInfo sysWxUserInfo) {
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
    public int deleteSysWxUserInfoByIds(String[] openids) {
        return sysWxUserInfoMapper.deleteSysWxUserInfoByIds(openids);
    }

    /**
     * 删除微信用户信息
     *
     * @param openid 微信用户ID
     * @return 结果
     */
    @Override
    public int deleteSysWxUserInfoById(String openid) {
        return sysWxUserInfoMapper.deleteSysWxUserInfoById(openid);
    }

    /**
     * 根据手机号查询微信用户
     * @param phone
     * @return
     */
    public SysWxUserInfo getSysWxUserInfoByPhone(String phone){
        return sysWxUserInfoMapper.getSysWxUserInfoByPhone(phone);
    }

    /**
     * 根据openid移除对应绑定的cusId
     * @param openid
     * @return
     */
    public int removeCusIdByOpenId(String openid){
        return sysWxUserInfoMapper.removeCusIdByOpenId(openid);
    }

    /**
     * 根据cusId更新健康减脂宣言
     * @param sysWxUserInfo
     * @return
     */
    public int updateHealthManifestoByCusId(SysWxUserInfo sysWxUserInfo){
        return sysWxUserInfoMapper.updateHealthManifestoByCusId(sysWxUserInfo);
    }
}
package com.ruoyi.project.axsystem.service.impl;

import java.util.List;

import com.ruoyi.common.core.lang.UUID;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.axsystem.mapper.UserBaseMapper;
import com.ruoyi.project.axsystem.domain.UserBase;
import com.ruoyi.project.axsystem.service.IUserBaseService;

/**
 * 用户基础信息Service业务层处理
 * 
 * @author joy
 * @date 2020-05-18
 */
@Service
public class UserBaseServiceImpl implements IUserBaseService 
{
    @Autowired
    private UserBaseMapper userBaseMapper;

    /**
     * 查询用户基础信息
     * 
     * @param uid 用户基础信息ID
     * @return 用户基础信息
     */
    @Override
    public UserBase selectUserBaseById(Long uid)
    {
        return userBaseMapper.selectUserBaseById(uid);
    }

    /**
     * 查询用户基础信息列表
     * 
     * @param userBase 用户基础信息
     * @return 用户基础信息
     */
    @Override
    public List<UserBase> selectUserBaseList(UserBase userBase)
    {
        return userBaseMapper.selectUserBaseList(userBase);
    }

    /**
     * 新增用户基础信息
     * 
     * @param userBase 用户基础信息
     * @return 结果
     */
    @Override
    public int insertUserBase(UserBase userBase)
    {
        userBase.setCreateTime(DateUtils.getNowDate());
        String uuid = UUID.randomUUID().toString();
        userBase.setUid(uuid);
        return userBaseMapper.insertUserBase(userBase);
    }

    /**
     * 修改用户基础信息
     * 
     * @param userBase 用户基础信息
     * @return 结果
     */
    @Override
    public int updateUserBase(UserBase userBase)
    {
        userBase.setUpdateTime(DateUtils.getNowDate());
        return userBaseMapper.updateUserBase(userBase);
    }

    /**
     * 批量删除用户基础信息
     * 
     * @param uids 需要删除的用户基础信息ID
     * @return 结果
     */
    @Override
    public int deleteUserBaseByIds(Long[] uids)
    {
        return userBaseMapper.deleteUserBaseByIds(uids);
    }

    /**
     * 删除用户基础信息信息
     * 
     * @param uid 用户基础信息ID
     * @return 结果
     */
    @Override
    public int deleteUserBaseById(Long uid)
    {
        return userBaseMapper.deleteUserBaseById(uid);
    }
}

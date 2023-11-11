package com.ruoyi.user.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.domain.UserInfo;
import com.ruoyi.user.service.IUserInfoService;

/**
 * 用户信息管理Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements IUserInfoService {

    private final UserInfoMapper userInfoMapper;

    /**
     * 查询用户信息管理
     *
     * @param id 用户信息管理主键
     * @return 用户信息管理
     */
    @Override
    public UserInfo selectUserInfoById(Long id) {
        return userInfoMapper.selectUserInfoById(id);
    }

    /**
     * 查询用户信息管理列表
     *
     * @param userInfo 用户信息管理
     * @return 用户信息管理
     */
    @Override
    public List<UserInfo> selectUserInfoList(UserInfo userInfo) {
        return userInfoMapper.selectUserInfoList(userInfo);
    }

    /**
     * 新增用户信息管理
     *
     * @param userInfo 用户信息管理
     * @return 结果
     */
    @Override
    public int insertUserInfo(UserInfo userInfo) {
        Date now = DateUtils.getNowDate();
        userInfo.setLastLoginTime(now)
                .setCreateTime(now);
        return userInfoMapper.insertUserInfo(userInfo);
    }

    /**
     * 修改用户信息管理
     *
     * @param userInfo 用户信息管理
     * @return 结果
     */
    @Override
    public int updateUserInfo(UserInfo userInfo) {
        userInfo.setLastLoginTime(DateUtils.getNowDate());
        return userInfoMapper.updateUserInfo(userInfo);
    }

    /**
     * 批量删除用户信息管理
     *
     * @param ids 需要删除的用户信息管理主键
     * @return 结果
     */
    @Override
    public int deleteUserInfoByIds(Long[] ids) {
        return userInfoMapper.deleteUserInfoByIds(ids);
    }

    /**
     * 删除用户信息管理信息
     *
     * @param id 用户信息管理主键
     * @return 结果
     */
    @Override
    public int deleteUserInfoById(Long id) {
        return userInfoMapper.deleteUserInfoById(id);
    }
}

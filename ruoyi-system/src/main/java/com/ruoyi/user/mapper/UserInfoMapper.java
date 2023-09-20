package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.UserInfo;

/**
 * 用户信息管理Mapper接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface UserInfoMapper {

    /**
     * 查询用户信息管理
     *
     * @param id 用户信息管理主键
     * @return 用户信息管理
     */
    public UserInfo selectUserInfoById(Long id);

    /**
     * 根据openId查询微信用户信息
     *
     * @param openId
     * @return 结果
     */
    public UserInfo selectByOpenId(String openId);

    /**
     * 查询用户信息管理列表
     *
     * @param userInfo 用户信息管理
     * @return 用户信息管理集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo userInfo);

    /**
     * 新增用户信息管理
     *
     * @param userInfo 用户信息管理
     * @return 结果
     */
    public int insertUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息管理
     *
     * @param userInfo 用户信息管理
     * @return 结果
     */
    public int updateUserInfo(UserInfo userInfo);

    /**
     * 删除用户信息管理
     *
     * @param id 用户信息管理主键
     * @return 结果
     */
    public int deleteUserInfoById(Long id);

    /**
     * 批量删除用户信息管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserInfoByIds(Long[] ids);
}

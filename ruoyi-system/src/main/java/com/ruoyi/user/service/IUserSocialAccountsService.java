package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.UserSocialAccounts;

/**
 * 用户社交账号管理Service接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface IUserSocialAccountsService {

    /**
     * 查询用户社交账号管理
     *
     * @param id 用户社交账号管理主键
     * @return 用户社交账号管理
     */
    public UserSocialAccounts selectUserSocialAccountsById(Long id);

    /**
     * 查询用户社交账号管理列表
     *
     * @param userSocialAccounts 用户社交账号管理
     * @return 用户社交账号管理集合
     */
    public List<UserSocialAccounts> selectUserSocialAccountsList(UserSocialAccounts userSocialAccounts);

    /**
     * 新增用户社交账号管理
     *
     * @param userSocialAccounts 用户社交账号管理
     * @return 结果
     */
    public int insertUserSocialAccounts(UserSocialAccounts userSocialAccounts);

    /**
     * 修改用户社交账号管理
     *
     * @param userSocialAccounts 用户社交账号管理
     * @return 结果
     */
    public int updateUserSocialAccounts(UserSocialAccounts userSocialAccounts);

    /**
     * 批量删除用户社交账号管理
     *
     * @param ids 需要删除的用户社交账号管理主键集合
     * @return 结果
     */
    public int deleteUserSocialAccountsByIds(Long[] ids);

    /**
     * 删除用户社交账号管理信息
     *
     * @param id 用户社交账号管理主键
     * @return 结果
     */
    public int deleteUserSocialAccountsById(Long id);
}

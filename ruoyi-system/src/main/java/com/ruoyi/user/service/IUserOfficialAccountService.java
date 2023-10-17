package com.ruoyi.user.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.user.domain.UserOfficialAccount;

/**
 * 微信公众号用户Service接口
 *
 * @author Lam
 * @date 2023-10-14
 */
public interface IUserOfficialAccountService {

    /**
     * 查询微信公众号用户
     *
     * @param id 微信公众号用户主键
     * @return 微信公众号用户
     */
    public UserOfficialAccount selectUserOfficialAccountById(Long id);

    /**
     * 查询微信公众号用户列表
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 微信公众号用户集合
     */
    public List<UserOfficialAccount> selectUserOfficialAccountList(UserOfficialAccount userOfficialAccount);

    /**
     * 新增微信公众号用户
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 结果
     */
    public int insertUserOfficialAccount(UserOfficialAccount userOfficialAccount);

    /**
     * 修改微信公众号用户
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 结果
     */
    public int updateUserOfficialAccount(UserOfficialAccount userOfficialAccount);

    /**
     * 批量删除微信公众号用户
     *
     * @param ids 需要删除的微信公众号用户主键集合
     * @return 结果
     */
    public int deleteUserOfficialAccountByIds(Long[] ids);

    /**
     * 删除微信公众号用户信息
     *
     * @param id 微信公众号用户主键
     * @return 结果
     */
    public int deleteUserOfficialAccountById(Long id);

    /**
     * 拉取微信公众号用户列表
     *
     * @return 结果
     * */
    public AjaxResult pullNewUserList();

    /**
     * 批量更新新用户基本信息
     *
     * @return 结果
     * */
    public AjaxResult updateNewUserInfo();

}

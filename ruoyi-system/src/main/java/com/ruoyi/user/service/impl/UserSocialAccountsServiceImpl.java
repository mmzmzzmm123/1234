package com.ruoyi.user.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserSocialAccountsMapper;
import com.ruoyi.user.domain.UserSocialAccounts;
import com.ruoyi.user.service.IUserSocialAccountsService;

/**
 * 用户社交账号管理Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class UserSocialAccountsServiceImpl implements IUserSocialAccountsService {

    private final UserSocialAccountsMapper userSocialAccountsMapper;

    /**
     * 查询用户社交账号管理
     *
     * @param id 用户社交账号管理主键
     * @return 用户社交账号管理
     */
    @Override
    public UserSocialAccounts selectUserSocialAccountsById(Long id) {
        return userSocialAccountsMapper.selectUserSocialAccountsById(id);
    }

    /**
     * 查询用户社交账号管理列表
     *
     * @param userSocialAccounts 用户社交账号管理
     * @return 用户社交账号管理
     */
    @Override
    public List<UserSocialAccounts> selectUserSocialAccountsList(UserSocialAccounts userSocialAccounts) {
        return userSocialAccountsMapper.selectUserSocialAccountsList(userSocialAccounts);
    }

    /**
     * 新增用户社交账号管理
     *
     * @param userSocialAccounts 用户社交账号管理
     * @return 结果
     */
    @Override
    public int insertUserSocialAccounts(UserSocialAccounts userSocialAccounts) {
        Date now = DateUtils.getNowDate();
        userSocialAccounts.setCreateTime(now)
                .setUpdateTime(now);
        return userSocialAccountsMapper.insertUserSocialAccounts(userSocialAccounts);
    }

    /**
     * 修改用户社交账号管理
     *
     * @param userSocialAccounts 用户社交账号管理
     * @return 结果
     */
    @Override
    public int updateUserSocialAccounts(UserSocialAccounts userSocialAccounts) {
        userSocialAccounts.setUpdateTime(DateUtils.getNowDate());
        return userSocialAccountsMapper.updateUserSocialAccounts(userSocialAccounts);
    }

    /**
     * 批量删除用户社交账号管理
     *
     * @param ids 需要删除的用户社交账号管理主键
     * @return 结果
     */
    @Override
    public int deleteUserSocialAccountsByIds(Long[] ids) {
        return userSocialAccountsMapper.deleteUserSocialAccountsByIds(ids);
    }

    /**
     * 删除用户社交账号管理信息
     *
     * @param id 用户社交账号管理主键
     * @return 结果
     */
    @Override
    public int deleteUserSocialAccountsById(Long id) {
        return userSocialAccountsMapper.deleteUserSocialAccountsById(id);
    }
}

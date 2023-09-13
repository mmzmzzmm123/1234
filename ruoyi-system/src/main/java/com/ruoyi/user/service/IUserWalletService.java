package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.UserWallet;

/**
 * 用户钱包管理Service接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface IUserWalletService {

    /**
     * 查询用户钱包管理
     *
     * @param id 用户钱包管理主键
     * @return 用户钱包管理
     */
    public UserWallet selectUserWalletById(Long id);

    /**
     * 查询用户钱包管理列表
     *
     * @param userWallet 用户钱包管理
     * @return 用户钱包管理集合
     */
    public List<UserWallet> selectUserWalletList(UserWallet userWallet);

    /**
     * 新增用户钱包管理
     *
     * @param userWallet 用户钱包管理
     * @return 结果
     */
    public int insertUserWallet(UserWallet userWallet);

    /**
     * 修改用户钱包管理
     *
     * @param userWallet 用户钱包管理
     * @return 结果
     */
    public int updateUserWallet(UserWallet userWallet);

    /**
     * 批量删除用户钱包管理
     *
     * @param ids 需要删除的用户钱包管理主键集合
     * @return 结果
     */
    public int deleteUserWalletByIds(Long[] ids);

    /**
     * 删除用户钱包管理信息
     *
     * @param id 用户钱包管理主键
     * @return 结果
     */
    public int deleteUserWalletById(Long id);
}

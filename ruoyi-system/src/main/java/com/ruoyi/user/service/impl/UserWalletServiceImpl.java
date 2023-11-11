package com.ruoyi.user.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserWalletMapper;
import com.ruoyi.user.domain.UserWallet;
import com.ruoyi.user.service.IUserWalletService;

/**
 * 用户钱包管理Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class UserWalletServiceImpl implements IUserWalletService {

    private final UserWalletMapper userWalletMapper;

    /**
     * 查询用户钱包管理
     *
     * @param id 用户钱包管理主键
     * @return 用户钱包管理
     */
    @Override
    public UserWallet selectUserWalletById(Long id) {
        return userWalletMapper.selectUserWalletById(id);
    }

    /**
     * 查询用户钱包管理列表
     *
     * @param userWallet 用户钱包管理
     * @return 用户钱包管理
     */
    @Override
    public List<UserWallet> selectUserWalletList(UserWallet userWallet) {
        return userWalletMapper.selectUserWalletList(userWallet);
    }

    /**
     * 新增用户钱包管理
     *
     * @param userWallet 用户钱包管理
     * @return 结果
     */
    @Override
    public int insertUserWallet(UserWallet userWallet) {
        return userWalletMapper.insertUserWallet(userWallet);
    }

    /**
     * 修改用户钱包管理
     *
     * @param userWallet 用户钱包管理
     * @return 结果
     */
    @Override
    public int updateUserWallet(UserWallet userWallet) {
        userWallet.setUpdateTime(DateUtils.getNowDate());
        return userWalletMapper.updateUserWallet(userWallet);
    }

    /**
     * 批量删除用户钱包管理
     *
     * @param ids 需要删除的用户钱包管理主键
     * @return 结果
     */
    @Override
    public int deleteUserWalletByIds(Long[] ids) {
        return userWalletMapper.deleteUserWalletByIds(ids);
    }

    /**
     * 删除用户钱包管理信息
     *
     * @param id 用户钱包管理主键
     * @return 结果
     */
    @Override
    public int deleteUserWalletById(Long id) {
        return userWalletMapper.deleteUserWalletById(id);
    }
}

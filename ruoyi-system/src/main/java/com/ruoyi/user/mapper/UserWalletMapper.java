package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.UserWallet;

/**
 * 用户钱包管理Mapper接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface UserWalletMapper {

    /**
     * 查询用户钱包管理
     *
     * @param id 用户钱包管理主键
     * @return 用户钱包管理
     */
    public UserWallet selectUserWalletById(Long id);

    /**
     * 根据用户标识查询记录
     *
     * @param userId 用户标识
     * @return 结果
     * */
    public UserWallet selectByUserId(Long userId);

    /**
     * 根据用户标识查询记录（悲观锁-行级别-为user_id字段添加普通索引即可）
     *
     * @param userId 用户标识
     * @return 结果
     * */
    public UserWallet selectByUserIdForUpdate(Long userId);

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
     * 删除用户钱包管理
     *
     * @param id 用户钱包管理主键
     * @return 结果
     */
    public int deleteUserWalletById(Long id);

    /**
     * 批量删除用户钱包管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserWalletByIds(Long[] ids);
}

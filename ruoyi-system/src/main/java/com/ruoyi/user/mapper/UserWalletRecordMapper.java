package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.UserWalletRecord;

/**
 * 用户钱包记录Mapper接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface UserWalletRecordMapper {

    /**
     * 查询用户钱包记录
     *
     * @param id 用户钱包记录主键
     * @return 用户钱包记录
     */
    public UserWalletRecord selectUserWalletRecordById(Long id);

    /**
     * 查询用户钱包记录列表
     *
     * @param userWalletRecord 用户钱包记录
     * @return 用户钱包记录集合
     */
    public List<UserWalletRecord> selectUserWalletRecordList(UserWalletRecord userWalletRecord);

    /**
     * 新增用户钱包记录
     *
     * @param userWalletRecord 用户钱包记录
     * @return 结果
     */
    public int insertUserWalletRecord(UserWalletRecord userWalletRecord);

    /**
     * 修改用户钱包记录
     *
     * @param userWalletRecord 用户钱包记录
     * @return 结果
     */
    public int updateUserWalletRecord(UserWalletRecord userWalletRecord);

    /**
     * 删除用户钱包记录
     *
     * @param id 用户钱包记录主键
     * @return 结果
     */
    public int deleteUserWalletRecordById(Long id);

    /**
     * 批量删除用户钱包记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserWalletRecordByIds(Long[] ids);
}

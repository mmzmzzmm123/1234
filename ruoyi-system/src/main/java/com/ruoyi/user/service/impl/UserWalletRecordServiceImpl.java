package com.ruoyi.user.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserWalletRecordMapper;
import com.ruoyi.user.domain.UserWalletRecord;
import com.ruoyi.user.service.IUserWalletRecordService;

/**
 * 用户钱包记录Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class UserWalletRecordServiceImpl implements IUserWalletRecordService {

    private final UserWalletRecordMapper userWalletRecordMapper;

    /**
     * 查询用户钱包记录
     *
     * @param id 用户钱包记录主键
     * @return 用户钱包记录
     */
    @Override
    public UserWalletRecord selectUserWalletRecordById(Long id) {
        return userWalletRecordMapper.selectUserWalletRecordById(id);
    }

    /**
     * 查询用户钱包记录列表
     *
     * @param userWalletRecord 用户钱包记录
     * @return 用户钱包记录
     */
    @Override
    public List<UserWalletRecord> selectUserWalletRecordList(UserWalletRecord userWalletRecord) {
        return userWalletRecordMapper.selectUserWalletRecordList(userWalletRecord);
    }

    /**
     * 新增用户钱包记录
     *
     * @param userWalletRecord 用户钱包记录
     * @return 结果
     */
    @Override
    public int insertUserWalletRecord(UserWalletRecord userWalletRecord) {
        userWalletRecord.setCreateTime(DateUtils.getNowDate());
        return userWalletRecordMapper.insertUserWalletRecord(userWalletRecord);
    }

    /**
     * 修改用户钱包记录
     *
     * @param userWalletRecord 用户钱包记录
     * @return 结果
     */
    @Override
    public int updateUserWalletRecord(UserWalletRecord userWalletRecord) {
        return userWalletRecordMapper.updateUserWalletRecord(userWalletRecord);
    }

    /**
     * 批量删除用户钱包记录
     *
     * @param ids 需要删除的用户钱包记录主键
     * @return 结果
     */
    @Override
    public int deleteUserWalletRecordByIds(Long[] ids) {
        return userWalletRecordMapper.deleteUserWalletRecordByIds(ids);
    }

    /**
     * 删除用户钱包记录信息
     *
     * @param id 用户钱包记录主键
     * @return 结果
     */
    @Override
    public int deleteUserWalletRecordById(Long id) {
        return userWalletRecordMapper.deleteUserWalletRecordById(id);
    }
}

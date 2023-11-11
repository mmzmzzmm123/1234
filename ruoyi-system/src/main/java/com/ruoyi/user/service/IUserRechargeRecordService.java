package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.UserRechargeRecord;

/**
 * 用户充值记录Service接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface IUserRechargeRecordService {

    /**
     * 查询用户充值记录
     *
     * @param id 用户充值记录主键
     * @return 用户充值记录
     */
    public UserRechargeRecord selectUserRechargeRecordById(Long id);

    /**
     * 查询用户充值记录列表
     *
     * @param userRechargeRecord 用户充值记录
     * @return 用户充值记录集合
     */
    public List<UserRechargeRecord> selectUserRechargeRecordList(UserRechargeRecord userRechargeRecord);

    /**
     * 新增用户充值记录
     *
     * @param userRechargeRecord 用户充值记录
     * @return 结果
     */
    public int insertUserRechargeRecord(UserRechargeRecord userRechargeRecord);

    /**
     * 修改用户充值记录
     *
     * @param userRechargeRecord 用户充值记录
     * @return 结果
     */
    public int updateUserRechargeRecord(UserRechargeRecord userRechargeRecord);

    /**
     * 批量删除用户充值记录
     *
     * @param ids 需要删除的用户充值记录主键集合
     * @return 结果
     */
    public int deleteUserRechargeRecordByIds(Long[] ids);

    /**
     * 删除用户充值记录信息
     *
     * @param id 用户充值记录主键
     * @return 结果
     */
    public int deleteUserRechargeRecordById(Long id);
}

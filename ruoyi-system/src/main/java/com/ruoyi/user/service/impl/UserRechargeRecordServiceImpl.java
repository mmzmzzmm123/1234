package com.ruoyi.user.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserRechargeRecordMapper;
import com.ruoyi.user.domain.UserRechargeRecord;
import com.ruoyi.user.service.IUserRechargeRecordService;

/**
 * 用户充值记录Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class UserRechargeRecordServiceImpl implements IUserRechargeRecordService {

    private final UserRechargeRecordMapper userRechargeRecordMapper;

    /**
     * 查询用户充值记录
     *
     * @param id 用户充值记录主键
     * @return 用户充值记录
     */
    @Override
    public UserRechargeRecord selectUserRechargeRecordById(Long id) {
        return userRechargeRecordMapper.selectUserRechargeRecordById(id);
    }

    /**
     * 查询用户充值记录列表
     *
     * @param userRechargeRecord 用户充值记录
     * @return 用户充值记录
     */
    @Override
    public List<UserRechargeRecord> selectUserRechargeRecordList(UserRechargeRecord userRechargeRecord) {
        return userRechargeRecordMapper.selectUserRechargeRecordList(userRechargeRecord);
    }

    /**
     * 新增用户充值记录
     *
     * @param userRechargeRecord 用户充值记录
     * @return 结果
     */
    @Override
    public int insertUserRechargeRecord(UserRechargeRecord userRechargeRecord) {
        Date now = DateUtils.getNowDate();
        userRechargeRecord.setCreateTime(now)
                .setUpdateTime(now);
        return userRechargeRecordMapper.insertUserRechargeRecord(userRechargeRecord);
    }

    /**
     * 修改用户充值记录
     *
     * @param userRechargeRecord 用户充值记录
     * @return 结果
     */
    @Override
    public int updateUserRechargeRecord(UserRechargeRecord userRechargeRecord) {
        userRechargeRecord.setUpdateTime(DateUtils.getNowDate());
        return userRechargeRecordMapper.updateUserRechargeRecord(userRechargeRecord);
    }

    /**
     * 批量删除用户充值记录
     *
     * @param ids 需要删除的用户充值记录主键
     * @return 结果
     */
    @Override
    public int deleteUserRechargeRecordByIds(Long[] ids) {
        return userRechargeRecordMapper.deleteUserRechargeRecordByIds(ids);
    }

    /**
     * 删除用户充值记录信息
     *
     * @param id 用户充值记录主键
     * @return 结果
     */
    @Override
    public int deleteUserRechargeRecordById(Long id) {
        return userRechargeRecordMapper.deleteUserRechargeRecordById(id);
    }
}

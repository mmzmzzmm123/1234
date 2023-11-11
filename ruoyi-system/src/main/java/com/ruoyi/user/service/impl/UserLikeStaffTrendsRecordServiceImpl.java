package com.ruoyi.user.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserLikeStaffTrendsRecordMapper;
import com.ruoyi.user.domain.UserLikeStaffTrendsRecord;
import com.ruoyi.user.service.IUserLikeStaffTrendsRecordService;

/**
 * 点赞动态记录Service业务层处理
 *
 * @author Lam
 * @date 2023-09-21
 */
@Service
@RequiredArgsConstructor
public class UserLikeStaffTrendsRecordServiceImpl implements IUserLikeStaffTrendsRecordService {

    private final UserLikeStaffTrendsRecordMapper userLikeStaffTrendsRecordMapper;

    /**
     * 查询点赞动态记录
     *
     * @param id 点赞动态记录主键
     * @return 点赞动态记录
     */
    @Override
    public UserLikeStaffTrendsRecord selectUserLikeStaffTrendsRecordById(Long id) {
        return userLikeStaffTrendsRecordMapper.selectUserLikeStaffTrendsRecordById(id);
    }

    /**
     * 查询点赞动态记录列表
     *
     * @param userLikeStaffTrendsRecord 点赞动态记录
     * @return 点赞动态记录
     */
    @Override
    public List<UserLikeStaffTrendsRecord> selectUserLikeStaffTrendsRecordList(UserLikeStaffTrendsRecord userLikeStaffTrendsRecord) {
        return userLikeStaffTrendsRecordMapper.selectUserLikeStaffTrendsRecordList(userLikeStaffTrendsRecord);
    }

    /**
     * 新增点赞动态记录
     *
     * @param userLikeStaffTrendsRecord 点赞动态记录
     * @return 结果
     */
    @Override
    public int insertUserLikeStaffTrendsRecord(UserLikeStaffTrendsRecord userLikeStaffTrendsRecord) {
        userLikeStaffTrendsRecord.setCreateTime(DateUtils.getNowDate());
        return userLikeStaffTrendsRecordMapper.insertUserLikeStaffTrendsRecord(userLikeStaffTrendsRecord);
    }

    /**
     * 修改点赞动态记录
     *
     * @param userLikeStaffTrendsRecord 点赞动态记录
     * @return 结果
     */
    @Override
    public int updateUserLikeStaffTrendsRecord(UserLikeStaffTrendsRecord userLikeStaffTrendsRecord) {
        return userLikeStaffTrendsRecordMapper.updateUserLikeStaffTrendsRecord(userLikeStaffTrendsRecord);
    }

    /**
     * 批量删除点赞动态记录
     *
     * @param ids 需要删除的点赞动态记录主键
     * @return 结果
     */
    @Override
    public int deleteUserLikeStaffTrendsRecordByIds(Long[] ids) {
        return userLikeStaffTrendsRecordMapper.deleteUserLikeStaffTrendsRecordByIds(ids);
    }

    /**
     * 删除点赞动态记录信息
     *
     * @param id 点赞动态记录主键
     * @return 结果
     */
    @Override
    public int deleteUserLikeStaffTrendsRecordById(Long id) {
        return userLikeStaffTrendsRecordMapper.deleteUserLikeStaffTrendsRecordById(id);
    }
}

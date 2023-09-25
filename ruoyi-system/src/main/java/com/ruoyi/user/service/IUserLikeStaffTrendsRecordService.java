package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.UserLikeStaffTrendsRecord;

/**
 * 点赞动态记录Service接口
 *
 * @author Lam
 * @date 2023-09-21
 */
public interface IUserLikeStaffTrendsRecordService {

    /**
     * 查询点赞动态记录
     *
     * @param id 点赞动态记录主键
     * @return 点赞动态记录
     */
    public UserLikeStaffTrendsRecord selectUserLikeStaffTrendsRecordById(Long id);

    /**
     * 查询点赞动态记录列表
     *
     * @param userLikeStaffTrendsRecord 点赞动态记录
     * @return 点赞动态记录集合
     */
    public List<UserLikeStaffTrendsRecord> selectUserLikeStaffTrendsRecordList(UserLikeStaffTrendsRecord userLikeStaffTrendsRecord);

    /**
     * 新增点赞动态记录
     *
     * @param userLikeStaffTrendsRecord 点赞动态记录
     * @return 结果
     */
    public int insertUserLikeStaffTrendsRecord(UserLikeStaffTrendsRecord userLikeStaffTrendsRecord);

    /**
     * 修改点赞动态记录
     *
     * @param userLikeStaffTrendsRecord 点赞动态记录
     * @return 结果
     */
    public int updateUserLikeStaffTrendsRecord(UserLikeStaffTrendsRecord userLikeStaffTrendsRecord);

    /**
     * 批量删除点赞动态记录
     *
     * @param ids 需要删除的点赞动态记录主键集合
     * @return 结果
     */
    public int deleteUserLikeStaffTrendsRecordByIds(Long[] ids);

    /**
     * 删除点赞动态记录信息
     *
     * @param id 点赞动态记录主键
     * @return 结果
     */
    public int deleteUserLikeStaffTrendsRecordById(Long id);
}

package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.UserLikeStaffTrendsRecord;

/**
 * 点赞动态记录Mapper接口
 *
 * @author Lam
 * @date 2023-09-21
 */
public interface UserLikeStaffTrendsRecordMapper {

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
     * 根据用户标识查询点赞动态标识集合
     *
     * @param userId 用户标识
     * @return 结果
     * */
    public List<Long> selectTrendsIdByUserId(Long userId);

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
     * 删除点赞动态记录
     *
     * @param id 点赞动态记录主键
     * @return 结果
     */
    public int deleteUserLikeStaffTrendsRecordById(Long id);

    /**
     * 批量删除点赞动态记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserLikeStaffTrendsRecordByIds(Long[] ids);
}

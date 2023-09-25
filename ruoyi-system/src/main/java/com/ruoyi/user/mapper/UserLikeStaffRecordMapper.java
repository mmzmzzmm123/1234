package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.UserLikeStaffRecord;

/**
 * 收藏员工记录Mapper接口
 *
 * @author Lam
 * @date 2023-09-21
 */
public interface UserLikeStaffRecordMapper {

    /**
     * 查询收藏员工记录
     *
     * @param id 收藏员工记录主键
     * @return 收藏员工记录
     */
    public UserLikeStaffRecord selectUserLikeStaffRecordById(Long id);

    /**
     * 查询收藏员工记录列表
     *
     * @param userLikeStaffRecord 收藏员工记录
     * @return 收藏员工记录集合
     */
    public List<UserLikeStaffRecord> selectUserLikeStaffRecordList(UserLikeStaffRecord userLikeStaffRecord);

    /**
     * 根据用户标识查询收藏员工标识集合
     *
     * @param userId 用户标识
     * @return 结果
     * */
    public List<Long> selectStaffUserIdByUserId(Long userId);

    /**
     * 新增收藏员工记录
     *
     * @param userLikeStaffRecord 收藏员工记录
     * @return 结果
     */
    public int insertUserLikeStaffRecord(UserLikeStaffRecord userLikeStaffRecord);

    /**
     * 修改收藏员工记录
     *
     * @param userLikeStaffRecord 收藏员工记录
     * @return 结果
     */
    public int updateUserLikeStaffRecord(UserLikeStaffRecord userLikeStaffRecord);

    /**
     * 删除收藏员工记录
     *
     * @param id 收藏员工记录主键
     * @return 结果
     */
    public int deleteUserLikeStaffRecordById(Long id);

    /**
     * 批量删除收藏员工记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserLikeStaffRecordByIds(Long[] ids);
}

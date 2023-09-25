package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.UserLikeStaffRecord;

/**
 * 收藏员工记录Service接口
 *
 * @author Lam
 * @date 2023-09-21
 */
public interface IUserLikeStaffRecordService {

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
     * 批量删除收藏员工记录
     *
     * @param ids 需要删除的收藏员工记录主键集合
     * @return 结果
     */
    public int deleteUserLikeStaffRecordByIds(Long[] ids);

    /**
     * 删除收藏员工记录信息
     *
     * @param id 收藏员工记录主键
     * @return 结果
     */
    public int deleteUserLikeStaffRecordById(Long id);
}

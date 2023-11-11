package com.ruoyi.user.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserLikeStaffRecordMapper;
import com.ruoyi.user.domain.UserLikeStaffRecord;
import com.ruoyi.user.service.IUserLikeStaffRecordService;

/**
 * 收藏员工记录Service业务层处理
 *
 * @author Lam
 * @date 2023-09-21
 */
@Service
@RequiredArgsConstructor
public class UserLikeStaffRecordServiceImpl implements IUserLikeStaffRecordService {

    private final UserLikeStaffRecordMapper userLikeStaffRecordMapper;

    /**
     * 查询收藏员工记录
     *
     * @param id 收藏员工记录主键
     * @return 收藏员工记录
     */
    @Override
    public UserLikeStaffRecord selectUserLikeStaffRecordById(Long id) {
        return userLikeStaffRecordMapper.selectUserLikeStaffRecordById(id);
    }

    /**
     * 查询收藏员工记录列表
     *
     * @param userLikeStaffRecord 收藏员工记录
     * @return 收藏员工记录
     */
    @Override
    public List<UserLikeStaffRecord> selectUserLikeStaffRecordList(UserLikeStaffRecord userLikeStaffRecord) {
        return userLikeStaffRecordMapper.selectUserLikeStaffRecordList(userLikeStaffRecord);
    }

    /**
     * 新增收藏员工记录
     *
     * @param userLikeStaffRecord 收藏员工记录
     * @return 结果
     */
    @Override
    public int insertUserLikeStaffRecord(UserLikeStaffRecord userLikeStaffRecord) {
        userLikeStaffRecord.setCreateTime(DateUtils.getNowDate());
        return userLikeStaffRecordMapper.insertUserLikeStaffRecord(userLikeStaffRecord);
    }

    /**
     * 修改收藏员工记录
     *
     * @param userLikeStaffRecord 收藏员工记录
     * @return 结果
     */
    @Override
    public int updateUserLikeStaffRecord(UserLikeStaffRecord userLikeStaffRecord) {
        return userLikeStaffRecordMapper.updateUserLikeStaffRecord(userLikeStaffRecord);
    }

    /**
     * 批量删除收藏员工记录
     *
     * @param ids 需要删除的收藏员工记录主键
     * @return 结果
     */
    @Override
    public int deleteUserLikeStaffRecordByIds(Long[] ids) {
        return userLikeStaffRecordMapper.deleteUserLikeStaffRecordByIds(ids);
    }

    /**
     * 删除收藏员工记录信息
     *
     * @param id 收藏员工记录主键
     * @return 结果
     */
    @Override
    public int deleteUserLikeStaffRecordById(Long id) {
        return userLikeStaffRecordMapper.deleteUserLikeStaffRecordById(id);
    }
}

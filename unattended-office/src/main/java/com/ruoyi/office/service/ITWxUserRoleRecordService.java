package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TWxUserRoleRecord;

/**
 * 微信用户邀请授权记录Service接口
 *
 * @author ruoyi
 * @date 2023-10-20
 */
public interface ITWxUserRoleRecordService
{
    /**
     * 查询微信用户邀请授权记录
     *
     * @param id 微信用户邀请授权记录主键
     * @return 微信用户邀请授权记录
     */
    public TWxUserRoleRecord selectTWxUserRoleRecordById(String id);

    /**
     * 查询微信用户邀请授权记录列表
     *
     * @param tWxUserRoleRecord 微信用户邀请授权记录
     * @return 微信用户邀请授权记录集合
     */
    public List<TWxUserRoleRecord> selectTWxUserRoleRecordList(TWxUserRoleRecord tWxUserRoleRecord);

    /**
     * 新增微信用户邀请授权记录
     *
     * @param tWxUserRoleRecord 微信用户邀请授权记录
     * @return 结果
     */
    public int insertTWxUserRoleRecord(TWxUserRoleRecord tWxUserRoleRecord);

    /**
     * 修改微信用户邀请授权记录
     *
     * @param tWxUserRoleRecord 微信用户邀请授权记录
     * @return 结果
     */
    public int updateTWxUserRoleRecord(TWxUserRoleRecord tWxUserRoleRecord);

    /**
     * 批量删除微信用户邀请授权记录
     *
     * @param ids 需要删除的微信用户邀请授权记录主键集合
     * @return 结果
     */
    public int deleteTWxUserRoleRecordByIds(String[] ids);

    /**
     * 删除微信用户邀请授权记录信息
     *
     * @param id 微信用户邀请授权记录主键
     * @return 结果
     */
    public int deleteTWxUserRoleRecordById(String id);
}

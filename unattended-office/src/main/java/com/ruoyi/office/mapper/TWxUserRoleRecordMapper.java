package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TWxUserRoleRecord;

/**
 * 微信用户邀请授权记录Mapper接口
 *
 * @author ruoyi
 * @date 2023-10-20
 */
public interface TWxUserRoleRecordMapper extends BaseMapper<TWxUserRoleRecord> {
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
     * 删除微信用户邀请授权记录
     *
     * @param id 微信用户邀请授权记录主键
     * @return 结果
     */
    public int deleteTWxUserRoleRecordById(String id);

    /**
     * 批量删除微信用户邀请授权记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTWxUserRoleRecordByIds(String[] ids);
}

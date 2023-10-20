package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserRoleRecordMapper;
import com.ruoyi.office.domain.TWxUserRoleRecord;
import com.ruoyi.office.service.ITWxUserRoleRecordService;

/**
 * 微信用户邀请授权记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-20
 */
@Service
public class TWxUserRoleRecordServiceImpl extends ServiceImpl<TWxUserRoleRecordMapper, TWxUserRoleRecord> implements ITWxUserRoleRecordService
{
    @Autowired
    private TWxUserRoleRecordMapper tWxUserRoleRecordMapper;

    /**
     * 查询微信用户邀请授权记录
     *
     * @param id 微信用户邀请授权记录主键
     * @return 微信用户邀请授权记录
     */
    @Override
    public TWxUserRoleRecord selectTWxUserRoleRecordById(String id)
    {
        return tWxUserRoleRecordMapper.selectTWxUserRoleRecordById(id);
    }

    /**
     * 查询微信用户邀请授权记录列表
     *
     * @param tWxUserRoleRecord 微信用户邀请授权记录
     * @return 微信用户邀请授权记录
     */
    @Override
    public List<TWxUserRoleRecord> selectTWxUserRoleRecordList(TWxUserRoleRecord tWxUserRoleRecord)
    {
        return tWxUserRoleRecordMapper.selectTWxUserRoleRecordList(tWxUserRoleRecord);
    }

    /**
     * 新增微信用户邀请授权记录
     *
     * @param tWxUserRoleRecord 微信用户邀请授权记录
     * @return 结果
     */
    @Override
    public int insertTWxUserRoleRecord(TWxUserRoleRecord tWxUserRoleRecord)
    {
        tWxUserRoleRecord.setId(UUID.fastUUID().toString(true));
        tWxUserRoleRecord.setCreateTime(DateUtils.getNowDate());
        return tWxUserRoleRecordMapper.insertTWxUserRoleRecord(tWxUserRoleRecord);
    }

    /**
     * 修改微信用户邀请授权记录
     *
     * @param tWxUserRoleRecord 微信用户邀请授权记录
     * @return 结果
     */
    @Override
    public int updateTWxUserRoleRecord(TWxUserRoleRecord tWxUserRoleRecord)
    {
        tWxUserRoleRecord.setUpdateTime(DateUtils.getNowDate());
        return tWxUserRoleRecordMapper.updateTWxUserRoleRecord(tWxUserRoleRecord);
    }

    /**
     * 批量删除微信用户邀请授权记录
     *
     * @param ids 需要删除的微信用户邀请授权记录主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserRoleRecordByIds(String[] ids)
    {
        return tWxUserRoleRecordMapper.deleteTWxUserRoleRecordByIds(ids);
    }

    /**
     * 删除微信用户邀请授权记录信息
     *
     * @param id 微信用户邀请授权记录主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserRoleRecordById(String id)
    {
        return tWxUserRoleRecordMapper.deleteTWxUserRoleRecordById(id);
    }
}

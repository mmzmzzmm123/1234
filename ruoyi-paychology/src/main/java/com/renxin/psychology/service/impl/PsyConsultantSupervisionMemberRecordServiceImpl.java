package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.renxin.psychology.mapper.PsyConsultantSupervisionMemberRecordMapper;
import com.renxin.psychology.domain.PsyConsultantSupervisionMemberRecord;
import com.renxin.psychology.service.IPsyConsultantSupervisionMemberRecordService;

/**
 * 督导笔记、成员评价 标签评价Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-26
 */
@Service
public class PsyConsultantSupervisionMemberRecordServiceImpl implements IPsyConsultantSupervisionMemberRecordService 
{
    @Autowired
    private PsyConsultantSupervisionMemberRecordMapper psyConsultantSupervisionMemberRecordMapper;

    /**
     * 查询督导笔记、成员评价 标签评价
     * 
     * @param id 督导笔记、成员评价 标签评价主键
     * @return 督导笔记、成员评价 标签评价
     */
    @Override
    public PsyConsultantSupervisionMemberRecord selectPsyConsultantSupervisionMemberRecordById(Long id)
    {
        return psyConsultantSupervisionMemberRecordMapper.selectPsyConsultantSupervisionMemberRecordById(id);
    }

    /**
     * 查询督导笔记、成员评价 标签评价列表
     * 
     * @param psyConsultantSupervisionMemberRecord 督导笔记、成员评价 标签评价
     * @return 督导笔记、成员评价 标签评价
     */
    @Override
    public List<PsyConsultantSupervisionMemberRecord> selectPsyConsultantSupervisionMemberRecordList(PsyConsultantSupervisionMemberRecord psyConsultantSupervisionMemberRecord)
    {
        return psyConsultantSupervisionMemberRecordMapper.selectPsyConsultantSupervisionMemberRecordList(psyConsultantSupervisionMemberRecord);
    }

    /**
     * 新增督导笔记、成员评价 标签评价
     * 
     * @param psyConsultantSupervisionMemberRecord 督导笔记、成员评价 标签评价
     * @return 结果
     */
    @Override
    public int insertPsyConsultantSupervisionMemberRecord(PsyConsultantSupervisionMemberRecord psyConsultantSupervisionMemberRecord)
    {
        psyConsultantSupervisionMemberRecord.setCreateTime(DateUtils.getNowDate());
        return psyConsultantSupervisionMemberRecordMapper.insertPsyConsultantSupervisionMemberRecord(psyConsultantSupervisionMemberRecord);
    }

    /**
     * 修改督导笔记、成员评价 标签评价
     * 
     * @param psyConsultantSupervisionMemberRecord 督导笔记、成员评价 标签评价
     * @return 结果
     */
    @Override
    public int updatePsyConsultantSupervisionMemberRecord(PsyConsultantSupervisionMemberRecord psyConsultantSupervisionMemberRecord)
    {
        psyConsultantSupervisionMemberRecord.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantSupervisionMemberRecordMapper.updatePsyConsultantSupervisionMemberRecord(psyConsultantSupervisionMemberRecord);
    }

    /**
     * 批量删除督导笔记、成员评价 标签评价
     * 
     * @param ids 需要删除的督导笔记、成员评价 标签评价主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantSupervisionMemberRecordByIds(Long[] ids)
    {
        return psyConsultantSupervisionMemberRecordMapper.deletePsyConsultantSupervisionMemberRecordByIds(ids);
    }

    /**
     * 删除督导笔记、成员评价 标签评价信息
     * 
     * @param id 督导笔记、成员评价 标签评价主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantSupervisionMemberRecordById(Long id)
    {
        return psyConsultantSupervisionMemberRecordMapper.deletePsyConsultantSupervisionMemberRecordById(id);
    }
}

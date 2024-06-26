package com.renxin.psychology.service;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultantSupervisionMemberRecord;

/**
 * 督导笔记、成员评价 标签评价Service接口
 * 
 * @author renxin
 * @date 2024-06-26
 */
public interface IPsyConsultantSupervisionMemberRecordService 
{
    /**
     * 查询督导笔记、成员评价 标签评价
     * 
     * @param id 督导笔记、成员评价 标签评价主键
     * @return 督导笔记、成员评价 标签评价
     */
    public PsyConsultantSupervisionMemberRecord selectPsyConsultantSupervisionMemberRecordById(Long id);

    /**
     * 查询督导笔记、成员评价 标签评价列表
     * 
     * @param psyConsultantSupervisionMemberRecord 督导笔记、成员评价 标签评价
     * @return 督导笔记、成员评价 标签评价集合
     */
    public List<PsyConsultantSupervisionMemberRecord> selectPsyConsultantSupervisionMemberRecordList(PsyConsultantSupervisionMemberRecord psyConsultantSupervisionMemberRecord);

    /**
     * 新增督导笔记、成员评价 标签评价
     * 
     * @param psyConsultantSupervisionMemberRecord 督导笔记、成员评价 标签评价
     * @return 结果
     */
    public int insertPsyConsultantSupervisionMemberRecord(PsyConsultantSupervisionMemberRecord psyConsultantSupervisionMemberRecord);

    /**
     * 修改督导笔记、成员评价 标签评价
     * 
     * @param psyConsultantSupervisionMemberRecord 督导笔记、成员评价 标签评价
     * @return 结果
     */
    public int updatePsyConsultantSupervisionMemberRecord(PsyConsultantSupervisionMemberRecord psyConsultantSupervisionMemberRecord);

    /**
     * 批量删除督导笔记、成员评价 标签评价
     * 
     * @param ids 需要删除的督导笔记、成员评价 标签评价主键集合
     * @return 结果
     */
    public int deletePsyConsultantSupervisionMemberRecordByIds(Long[] ids);

    /**
     * 删除督导笔记、成员评价 标签评价信息
     * 
     * @param id 督导笔记、成员评价 标签评价主键
     * @return 结果
     */
    public int deletePsyConsultantSupervisionMemberRecordById(Long id);
}

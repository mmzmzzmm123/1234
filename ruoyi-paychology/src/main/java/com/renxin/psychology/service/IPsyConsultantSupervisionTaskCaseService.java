package com.renxin.psychology.service;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultantSupervisionTaskCase;

/**
 * 督导任务个案Service接口
 * 
 * @author renxin
 * @date 2024-06-26
 */
public interface IPsyConsultantSupervisionTaskCaseService 
{
    /**
     * 查询督导任务个案
     * 
     * @param taskId 督导任务个案主键
     * @return 督导任务个案
     */
    public PsyConsultantSupervisionTaskCase selectPsyConsultantSupervisionTaskCaseByTaskId(Long taskId);

    /**
     * 查询督导任务个案列表
     * 
     * @param psyConsultantSupervisionTaskCase 督导任务个案
     * @return 督导任务个案集合
     */
    public List<PsyConsultantSupervisionTaskCase> selectPsyConsultantSupervisionTaskCaseList(PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase);

    /**
     * 新增督导任务个案
     * 
     * @param psyConsultantSupervisionTaskCase 督导任务个案
     * @return 结果
     */
    public int insertPsyConsultantSupervisionTaskCase(PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase);

    /**
     * 修改督导任务个案
     * 
     * @param psyConsultantSupervisionTaskCase 督导任务个案
     * @return 结果
     */
    public int updatePsyConsultantSupervisionTaskCase(PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase);

    /**
     * 批量删除督导任务个案
     * 
     * @param taskIds 需要删除的督导任务个案主键集合
     * @return 结果
     */
    public int deletePsyConsultantSupervisionTaskCaseByTaskIds(Long[] taskIds);

    /**
     * 删除督导任务个案信息
     * 
     * @param taskId 督导任务个案主键
     * @return 结果
     */
    public int deletePsyConsultantSupervisionTaskCaseByTaskId(Long taskId);
}

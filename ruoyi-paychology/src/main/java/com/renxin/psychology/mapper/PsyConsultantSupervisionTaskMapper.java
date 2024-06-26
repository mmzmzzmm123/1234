package com.renxin.psychology.mapper;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultantSupervisionTask;

/**
 * 咨询师团督任务Mapper接口
 * 
 * @author renxin
 * @date 2024-06-26
 */
public interface PsyConsultantSupervisionTaskMapper 
{
    /**
     * 查询咨询师团督任务
     * 
     * @param taskId 咨询师团督任务主键
     * @return 咨询师团督任务
     */
    public PsyConsultantSupervisionTask selectPsyConsultantSupervisionTaskByTaskId(Long taskId);

    /**
     * 查询咨询师团督任务列表
     * 
     * @param psyConsultantSupervisionTask 咨询师团督任务
     * @return 咨询师团督任务集合
     */
    public List<PsyConsultantSupervisionTask> selectPsyConsultantSupervisionTaskList(PsyConsultantSupervisionTask psyConsultantSupervisionTask);

    /**
     * 新增咨询师团督任务
     * 
     * @param psyConsultantSupervisionTask 咨询师团督任务
     * @return 结果
     */
    public int insertPsyConsultantSupervisionTask(PsyConsultantSupervisionTask psyConsultantSupervisionTask);

    /**
     * 修改咨询师团督任务
     * 
     * @param psyConsultantSupervisionTask 咨询师团督任务
     * @return 结果
     */
    public int updatePsyConsultantSupervisionTask(PsyConsultantSupervisionTask psyConsultantSupervisionTask);

    /**
     * 删除咨询师团督任务
     * 
     * @param taskId 咨询师团督任务主键
     * @return 结果
     */
    public int deletePsyConsultantSupervisionTaskByTaskId(Long taskId);

    /**
     * 批量删除咨询师团督任务
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantSupervisionTaskByTaskIds(Long[] taskIds);
}

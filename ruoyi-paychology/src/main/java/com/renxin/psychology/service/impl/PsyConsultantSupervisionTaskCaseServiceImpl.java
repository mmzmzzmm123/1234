package com.renxin.psychology.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.renxin.psychology.mapper.PsyConsultantSupervisionTaskCaseMapper;
import com.renxin.psychology.domain.PsyConsultantSupervisionTaskCase;
import com.renxin.psychology.service.IPsyConsultantSupervisionTaskCaseService;

/**
 * 督导任务个案Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-26
 */
@Service
public class PsyConsultantSupervisionTaskCaseServiceImpl implements IPsyConsultantSupervisionTaskCaseService 
{
    @Autowired
    private PsyConsultantSupervisionTaskCaseMapper psyConsultantSupervisionTaskCaseMapper;

    /**
     * 查询督导任务个案
     * 
     * @param taskId 督导任务个案主键
     * @return 督导任务个案
     */
    @Override
    public PsyConsultantSupervisionTaskCase selectPsyConsultantSupervisionTaskCaseByTaskId(Long taskId)
    {
        return psyConsultantSupervisionTaskCaseMapper.selectPsyConsultantSupervisionTaskCaseByTaskId(taskId);
    }

    /**
     * 查询督导任务个案列表
     * 
     * @param psyConsultantSupervisionTaskCase 督导任务个案
     * @return 督导任务个案
     */
    @Override
    public List<PsyConsultantSupervisionTaskCase> selectPsyConsultantSupervisionTaskCaseList(PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase)
    {
        return psyConsultantSupervisionTaskCaseMapper.selectPsyConsultantSupervisionTaskCaseList(psyConsultantSupervisionTaskCase);
    }

    /**
     * 新增督导任务个案
     * 
     * @param psyConsultantSupervisionTaskCase 督导任务个案
     * @return 结果
     */
    @Override
    public int insertPsyConsultantSupervisionTaskCase(PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase)
    {
        return psyConsultantSupervisionTaskCaseMapper.insertPsyConsultantSupervisionTaskCase(psyConsultantSupervisionTaskCase);
    }

    /**
     * 修改督导任务个案
     * 
     * @param psyConsultantSupervisionTaskCase 督导任务个案
     * @return 结果
     */
    @Override
    public int updatePsyConsultantSupervisionTaskCase(PsyConsultantSupervisionTaskCase psyConsultantSupervisionTaskCase)
    {
        return psyConsultantSupervisionTaskCaseMapper.updatePsyConsultantSupervisionTaskCase(psyConsultantSupervisionTaskCase);
    }

    /**
     * 批量删除督导任务个案
     * 
     * @param taskIds 需要删除的督导任务个案主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantSupervisionTaskCaseByTaskIds(Long[] taskIds)
    {
        return psyConsultantSupervisionTaskCaseMapper.deletePsyConsultantSupervisionTaskCaseByTaskIds(taskIds);
    }

    /**
     * 删除督导任务个案信息
     * 
     * @param taskId 督导任务个案主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantSupervisionTaskCaseByTaskId(Long taskId)
    {
        return psyConsultantSupervisionTaskCaseMapper.deletePsyConsultantSupervisionTaskCaseByTaskId(taskId);
    }
}

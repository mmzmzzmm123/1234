package com.baoli.task.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.task.mapper.BaoliBizYearTaskPlanEvaluateMapper;
import com.baoli.task.domain.BaoliBizYearTaskPlanEvaluate;
import com.baoli.task.service.IBaoliBizYearTaskPlanEvaluateService;

/**
 * 年任务考核Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-14
 */
@Service
public class BaoliBizYearTaskPlanEvaluateServiceImpl implements IBaoliBizYearTaskPlanEvaluateService 
{
    @Autowired
    private BaoliBizYearTaskPlanEvaluateMapper baoliBizYearTaskPlanEvaluateMapper;

    /**
     * 查询年任务考核
     * 
     * @param id 年任务考核主键
     * @return 年任务考核
     */
    @Override
    public BaoliBizYearTaskPlanEvaluate selectBaoliBizYearTaskPlanEvaluateById(Long id)
    {
        return baoliBizYearTaskPlanEvaluateMapper.selectBaoliBizYearTaskPlanEvaluateById(id);
    }

    /**
     * 查询年任务考核列表
     * 
     * @param baoliBizYearTaskPlanEvaluate 年任务考核
     * @return 年任务考核
     */
    @Override
    public List<BaoliBizYearTaskPlanEvaluate> selectBaoliBizYearTaskPlanEvaluateList(BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate)
    {
        return baoliBizYearTaskPlanEvaluateMapper.selectBaoliBizYearTaskPlanEvaluateList(baoliBizYearTaskPlanEvaluate);
    }

    /**
     * 新增年任务考核
     * 
     * @param baoliBizYearTaskPlanEvaluate 年任务考核
     * @return 结果
     */
    @Override
    public int insertBaoliBizYearTaskPlanEvaluate(BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate)
    {
        baoliBizYearTaskPlanEvaluate.setCreateTime(DateUtils.getNowDate());
        return baoliBizYearTaskPlanEvaluateMapper.insertBaoliBizYearTaskPlanEvaluate(baoliBizYearTaskPlanEvaluate);
    }

    /**
     * 修改年任务考核
     * 
     * @param baoliBizYearTaskPlanEvaluate 年任务考核
     * @return 结果
     */
    @Override
    public int updateBaoliBizYearTaskPlanEvaluate(BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate)
    {
        return baoliBizYearTaskPlanEvaluateMapper.updateBaoliBizYearTaskPlanEvaluate(baoliBizYearTaskPlanEvaluate);
    }

    /**
     * 批量删除年任务考核
     * 
     * @param ids 需要删除的年任务考核主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizYearTaskPlanEvaluateByIds(Long[] ids)
    {
        return baoliBizYearTaskPlanEvaluateMapper.deleteBaoliBizYearTaskPlanEvaluateByIds(ids);
    }

    /**
     * 删除年任务考核信息
     * 
     * @param id 年任务考核主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizYearTaskPlanEvaluateById(Long id)
    {
        return baoliBizYearTaskPlanEvaluateMapper.deleteBaoliBizYearTaskPlanEvaluateById(id);
    }
}

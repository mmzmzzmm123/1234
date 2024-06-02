package com.baoli.task.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.task.mapper.BaoliBizYearTaskPlanMapper;
import com.baoli.task.domain.BaoliBizYearTaskPlan;
import com.baoli.task.service.IBaoliBizYearTaskPlanService;

/**
 * 年任务管理（T）Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-14
 */
@Service
public class BaoliBizYearTaskPlanServiceImpl implements IBaoliBizYearTaskPlanService 
{
    @Autowired
    private BaoliBizYearTaskPlanMapper baoliBizYearTaskPlanMapper;

    /**
     * 查询年任务管理（T）
     * 
     * @param id 年任务管理（T）主键
     * @return 年任务管理（T）
     */
    @Override
    public BaoliBizYearTaskPlan selectBaoliBizYearTaskPlanById(Long id)
    {
        return baoliBizYearTaskPlanMapper.selectBaoliBizYearTaskPlanById(id);
    }

    /**
     * 查询年任务管理（T）列表
     * 
     * @param baoliBizYearTaskPlan 年任务管理（T）
     * @return 年任务管理（T）
     */
    @Override
    public List<BaoliBizYearTaskPlan> selectBaoliBizYearTaskPlanList(BaoliBizYearTaskPlan baoliBizYearTaskPlan)
    {
        return baoliBizYearTaskPlanMapper.selectBaoliBizYearTaskPlanList(baoliBizYearTaskPlan);
    }

    /**
     * 新增年任务管理（T）
     * 
     * @param baoliBizYearTaskPlan 年任务管理（T）
     * @return 结果
     */
    @Override
    public int insertBaoliBizYearTaskPlan(BaoliBizYearTaskPlan baoliBizYearTaskPlan)
    {
        baoliBizYearTaskPlan.setCreateTime(DateUtils.getNowDate());
        return baoliBizYearTaskPlanMapper.insertBaoliBizYearTaskPlan(baoliBizYearTaskPlan);
    }

    /**
     * 修改年任务管理（T）
     * 
     * @param baoliBizYearTaskPlan 年任务管理（T）
     * @return 结果
     */
    @Override
    public int updateBaoliBizYearTaskPlan(BaoliBizYearTaskPlan baoliBizYearTaskPlan)
    {
        return baoliBizYearTaskPlanMapper.updateBaoliBizYearTaskPlan(baoliBizYearTaskPlan);
    }

    /**
     * 批量删除年任务管理（T）
     * 
     * @param ids 需要删除的年任务管理（T）主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizYearTaskPlanByIds(Long[] ids)
    {
        return baoliBizYearTaskPlanMapper.deleteBaoliBizYearTaskPlanByIds(ids);
    }

    /**
     * 删除年任务管理（T）信息
     * 
     * @param id 年任务管理（T）主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizYearTaskPlanById(Long id)
    {
        return baoliBizYearTaskPlanMapper.deleteBaoliBizYearTaskPlanById(id);
    }
}

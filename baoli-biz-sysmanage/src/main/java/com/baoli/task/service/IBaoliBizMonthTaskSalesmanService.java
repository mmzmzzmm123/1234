package com.baoli.task.service;

import java.util.List;
import com.baoli.task.domain.BaoliBizMonthTaskSalesman;

/**
 * 驻店月度任务Service接口
 * 
 * @author niujs
 * @date 2024-05-14
 */
public interface IBaoliBizMonthTaskSalesmanService 
{
    /**
     * 查询驻店月度任务
     * 
     * @param id 驻店月度任务主键
     * @return 驻店月度任务
     */
    public BaoliBizMonthTaskSalesman selectBaoliBizMonthTaskSalesmanById(Long id);

    /**
     * 查询驻店月度任务列表
     * 
     * @param baoliBizMonthTaskSalesman 驻店月度任务
     * @return 驻店月度任务集合
     */
    public List<BaoliBizMonthTaskSalesman> selectBaoliBizMonthTaskSalesmanList(BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman);

    /**
     * 新增驻店月度任务
     * 
     * @param baoliBizMonthTaskSalesman 驻店月度任务
     * @return 结果
     */
    public int insertBaoliBizMonthTaskSalesman(BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman);

    /**
     * 修改驻店月度任务
     * 
     * @param baoliBizMonthTaskSalesman 驻店月度任务
     * @return 结果
     */
    public int updateBaoliBizMonthTaskSalesman(BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman);

    /**
     * 批量删除驻店月度任务
     * 
     * @param ids 需要删除的驻店月度任务主键集合
     * @return 结果
     */
    public int deleteBaoliBizMonthTaskSalesmanByIds(Long[] ids);

    /**
     * 删除驻店月度任务信息
     * 
     * @param id 驻店月度任务主键
     * @return 结果
     */
    public int deleteBaoliBizMonthTaskSalesmanById(Long id);
}

package com.baoli.task.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.task.mapper.BaoliBizMonthTaskSalesmanMapper;
import com.baoli.task.domain.BaoliBizMonthTaskSalesman;
import com.baoli.task.service.IBaoliBizMonthTaskSalesmanService;

/**
 * 驻店月度任务Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-14
 */
@Service
public class BaoliBizMonthTaskSalesmanServiceImpl implements IBaoliBizMonthTaskSalesmanService 
{
    @Autowired
    private BaoliBizMonthTaskSalesmanMapper baoliBizMonthTaskSalesmanMapper;

    /**
     * 查询驻店月度任务
     * 
     * @param id 驻店月度任务主键
     * @return 驻店月度任务
     */
    @Override
    public BaoliBizMonthTaskSalesman selectBaoliBizMonthTaskSalesmanById(Long id)
    {
        return baoliBizMonthTaskSalesmanMapper.selectBaoliBizMonthTaskSalesmanById(id);
    }

    /**
     * 查询驻店月度任务列表
     * 
     * @param baoliBizMonthTaskSalesman 驻店月度任务
     * @return 驻店月度任务
     */
    @Override
    public List<BaoliBizMonthTaskSalesman> selectBaoliBizMonthTaskSalesmanList(BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman)
    {
        return baoliBizMonthTaskSalesmanMapper.selectBaoliBizMonthTaskSalesmanList(baoliBizMonthTaskSalesman);
    }

    /**
     * 新增驻店月度任务
     * 
     * @param baoliBizMonthTaskSalesman 驻店月度任务
     * @return 结果
     */
    @Override
    public int insertBaoliBizMonthTaskSalesman(BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman)
    {
        return baoliBizMonthTaskSalesmanMapper.insertBaoliBizMonthTaskSalesman(baoliBizMonthTaskSalesman);
    }

    /**
     * 修改驻店月度任务
     * 
     * @param baoliBizMonthTaskSalesman 驻店月度任务
     * @return 结果
     */
    @Override
    public int updateBaoliBizMonthTaskSalesman(BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman)
    {
        return baoliBizMonthTaskSalesmanMapper.updateBaoliBizMonthTaskSalesman(baoliBizMonthTaskSalesman);
    }

    /**
     * 批量删除驻店月度任务
     * 
     * @param ids 需要删除的驻店月度任务主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizMonthTaskSalesmanByIds(Long[] ids)
    {
        return baoliBizMonthTaskSalesmanMapper.deleteBaoliBizMonthTaskSalesmanByIds(ids);
    }

    /**
     * 删除驻店月度任务信息
     * 
     * @param id 驻店月度任务主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizMonthTaskSalesmanById(Long id)
    {
        return baoliBizMonthTaskSalesmanMapper.deleteBaoliBizMonthTaskSalesmanById(id);
    }
}

package com.baoli.task.service;

import java.util.List;
import com.baoli.task.domain.BaoliBizMonthTaskStore;

/**
 * 商户月度任务Service接口
 * 
 * @author niujs
 * @date 2024-05-14
 */
public interface IBaoliBizMonthTaskStoreService 
{
    /**
     * 查询商户月度任务
     * 
     * @param id 商户月度任务主键
     * @return 商户月度任务
     */
    public BaoliBizMonthTaskStore selectBaoliBizMonthTaskStoreById(Long id);

    /**
     * 查询商户月度任务列表
     * 
     * @param baoliBizMonthTaskStore 商户月度任务
     * @return 商户月度任务集合
     */
    public List<BaoliBizMonthTaskStore> selectBaoliBizMonthTaskStoreList(BaoliBizMonthTaskStore baoliBizMonthTaskStore);

    /**
     * 新增商户月度任务
     * 
     * @param baoliBizMonthTaskStore 商户月度任务
     * @return 结果
     */
    public int insertBaoliBizMonthTaskStore(BaoliBizMonthTaskStore baoliBizMonthTaskStore);

    /**
     * 修改商户月度任务
     * 
     * @param baoliBizMonthTaskStore 商户月度任务
     * @return 结果
     */
    public int updateBaoliBizMonthTaskStore(BaoliBizMonthTaskStore baoliBizMonthTaskStore);

    /**
     * 批量删除商户月度任务
     * 
     * @param ids 需要删除的商户月度任务主键集合
     * @return 结果
     */
    public int deleteBaoliBizMonthTaskStoreByIds(Long[] ids);

    /**
     * 删除商户月度任务信息
     * 
     * @param id 商户月度任务主键
     * @return 结果
     */
    public int deleteBaoliBizMonthTaskStoreById(Long id);
}

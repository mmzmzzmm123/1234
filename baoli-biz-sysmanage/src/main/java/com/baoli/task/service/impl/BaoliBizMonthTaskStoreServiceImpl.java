package com.baoli.task.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.task.mapper.BaoliBizMonthTaskStoreMapper;
import com.baoli.task.domain.BaoliBizMonthTaskStore;
import com.baoli.task.service.IBaoliBizMonthTaskStoreService;

/**
 * 商户月度任务Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-14
 */
@Service
public class BaoliBizMonthTaskStoreServiceImpl implements IBaoliBizMonthTaskStoreService 
{
    @Autowired
    private BaoliBizMonthTaskStoreMapper baoliBizMonthTaskStoreMapper;

    /**
     * 查询商户月度任务
     * 
     * @param id 商户月度任务主键
     * @return 商户月度任务
     */
    @Override
    public BaoliBizMonthTaskStore selectBaoliBizMonthTaskStoreById(Long id)
    {
        return baoliBizMonthTaskStoreMapper.selectBaoliBizMonthTaskStoreById(id);
    }

    /**
     * 查询商户月度任务列表
     * 
     * @param baoliBizMonthTaskStore 商户月度任务
     * @return 商户月度任务
     */
    @Override
    public List<BaoliBizMonthTaskStore> selectBaoliBizMonthTaskStoreList(BaoliBizMonthTaskStore baoliBizMonthTaskStore)
    {
        return baoliBizMonthTaskStoreMapper.selectBaoliBizMonthTaskStoreList(baoliBizMonthTaskStore);
    }

    /**
     * 新增商户月度任务
     * 
     * @param baoliBizMonthTaskStore 商户月度任务
     * @return 结果
     */
    @Override
    public int insertBaoliBizMonthTaskStore(BaoliBizMonthTaskStore baoliBizMonthTaskStore)
    {
        return baoliBizMonthTaskStoreMapper.insertBaoliBizMonthTaskStore(baoliBizMonthTaskStore);
    }

    /**
     * 修改商户月度任务
     * 
     * @param baoliBizMonthTaskStore 商户月度任务
     * @return 结果
     */
    @Override
    public int updateBaoliBizMonthTaskStore(BaoliBizMonthTaskStore baoliBizMonthTaskStore)
    {
        return baoliBizMonthTaskStoreMapper.updateBaoliBizMonthTaskStore(baoliBizMonthTaskStore);
    }

    /**
     * 批量删除商户月度任务
     * 
     * @param ids 需要删除的商户月度任务主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizMonthTaskStoreByIds(Long[] ids)
    {
        return baoliBizMonthTaskStoreMapper.deleteBaoliBizMonthTaskStoreByIds(ids);
    }

    /**
     * 删除商户月度任务信息
     * 
     * @param id 商户月度任务主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizMonthTaskStoreById(Long id)
    {
        return baoliBizMonthTaskStoreMapper.deleteBaoliBizMonthTaskStoreById(id);
    }
}

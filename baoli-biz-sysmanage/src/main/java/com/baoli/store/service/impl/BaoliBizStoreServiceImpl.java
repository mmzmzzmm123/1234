package com.baoli.store.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.store.mapper.BaoliBizStoreMapper;
import com.baoli.store.domain.BaoliBizStore;
import com.baoli.store.service.IBaoliBizStoreService;

/**
 * 商户管理Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-08
 */
@Service
public class BaoliBizStoreServiceImpl implements IBaoliBizStoreService 
{
    @Autowired
    private BaoliBizStoreMapper baoliBizStoreMapper;

    /**
     * 查询商户管理
     * 
     * @param id 商户管理主键
     * @return 商户管理
     */
    @Override
    public BaoliBizStore selectBaoliBizStoreById(Long id)
    {
        return baoliBizStoreMapper.selectBaoliBizStoreById(id);
    }

    /**
     * 查询商户管理列表
     * 
     * @param baoliBizStore 商户管理
     * @return 商户管理
     */
    @Override
    public List<BaoliBizStore> selectBaoliBizStoreList(BaoliBizStore baoliBizStore)
    {
        return baoliBizStoreMapper.selectBaoliBizStoreList(baoliBizStore);
    }

    /**
     * 新增商户管理
     * 
     * @param baoliBizStore 商户管理
     * @return 结果
     */
    @Override
    public int insertBaoliBizStore(BaoliBizStore baoliBizStore)
    {
        baoliBizStore.setCreateTime(DateUtils.getNowDate());
        return baoliBizStoreMapper.insertBaoliBizStore(baoliBizStore);
    }

    /**
     * 修改商户管理
     * 
     * @param baoliBizStore 商户管理
     * @return 结果
     */
    @Override
    public int updateBaoliBizStore(BaoliBizStore baoliBizStore)
    {
        baoliBizStore.setUpdateTime(DateUtils.getNowDate());
        return baoliBizStoreMapper.updateBaoliBizStore(baoliBizStore);
    }

    /**
     * 批量删除商户管理
     * 
     * @param ids 需要删除的商户管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizStoreByIds(Long[] ids)
    {
        return baoliBizStoreMapper.deleteBaoliBizStoreByIds(ids);
    }

    /**
     * 删除商户管理信息
     * 
     * @param id 商户管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizStoreById(Long id)
    {
        return baoliBizStoreMapper.deleteBaoliBizStoreById(id);
    }
}

package com.baoli.store.mapper;

import java.util.List;
import com.baoli.store.domain.BaoliBizStore;

/**
 * 商户管理Mapper接口
 * 
 * @author niujs
 * @date 2024-04-08
 */
public interface BaoliBizStoreMapper 
{
    /**
     * 查询商户管理
     * 
     * @param id 商户管理主键
     * @return 商户管理
     */
    public BaoliBizStore selectBaoliBizStoreById(Long id);

    /**
     * 查询商户管理列表
     * 
     * @param baoliBizStore 商户管理
     * @return 商户管理集合
     */
    public List<BaoliBizStore> selectBaoliBizStoreList(BaoliBizStore baoliBizStore);

    /**
     * 新增商户管理
     * 
     * @param baoliBizStore 商户管理
     * @return 结果
     */
    public int insertBaoliBizStore(BaoliBizStore baoliBizStore);

    /**
     * 修改商户管理
     * 
     * @param baoliBizStore 商户管理
     * @return 结果
     */
    public int updateBaoliBizStore(BaoliBizStore baoliBizStore);

    /**
     * 删除商户管理
     * 
     * @param id 商户管理主键
     * @return 结果
     */
    public int deleteBaoliBizStoreById(Long id);

    /**
     * 批量删除商户管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizStoreByIds(Long[] ids);
}

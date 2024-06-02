package com.baoli.store.service;

import java.util.List;
import com.baoli.store.domain.BaoliBizFeeRateSchema;

/**
 * 商户费率计划Service接口
 * 
 * @author niujs
 * @date 2024-04-15
 */
public interface IBaoliBizFeeRateSchemaService 
{
    /**
     * 查询商户费率计划
     * 
     * @param id 商户费率计划主键
     * @return 商户费率计划
     */
    public BaoliBizFeeRateSchema selectBaoliBizFeeRateSchemaById(Long id);

    /**
     * 查询商户费率计划列表
     * 
     * @param baoliBizFeeRateSchema 商户费率计划
     * @return 商户费率计划集合
     */
    public List<BaoliBizFeeRateSchema> selectBaoliBizFeeRateSchemaList(BaoliBizFeeRateSchema baoliBizFeeRateSchema);

    /**
     * 新增商户费率计划
     * 
     * @param baoliBizFeeRateSchema 商户费率计划
     * @return 结果
     */
    public int insertBaoliBizFeeRateSchema(BaoliBizFeeRateSchema baoliBizFeeRateSchema);
    public int insertBaoliBizFeeRateSchemaBatch(List<BaoliBizFeeRateSchema> baoliBizFeeRateSchema);
    /**
     * 修改商户费率计划
     * 
     * @param baoliBizFeeRateSchema 商户费率计划
     * @return 结果
     */
    public int updateBaoliBizFeeRateSchema(BaoliBizFeeRateSchema baoliBizFeeRateSchema);

    /**
     * 批量删除商户费率计划
     * 
     * @param ids 需要删除的商户费率计划主键集合
     * @return 结果
     */
    public int deleteBaoliBizFeeRateSchemaByIds(Long[] ids);

    /**
     * 删除商户费率计划信息
     * 
     * @param id 商户费率计划主键
     * @return 结果
     */
    public int deleteBaoliBizFeeRateSchemaById(Long id);
}

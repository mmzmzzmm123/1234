package com.baoli.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.store.mapper.BaoliBizFeeRateSchemaMapper;
import com.baoli.store.domain.BaoliBizFeeRateSchema;
import com.baoli.store.service.IBaoliBizFeeRateSchemaService;

/**
 * 商户费率计划Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-15
 */
@Service
public class BaoliBizFeeRateSchemaServiceImpl implements IBaoliBizFeeRateSchemaService 
{
    @Autowired
    private BaoliBizFeeRateSchemaMapper baoliBizFeeRateSchemaMapper;

    @Override
    public int insertBaoliBizFeeRateSchemaBatch(List<BaoliBizFeeRateSchema> baoliBizFeeRateSchema) {
        return baoliBizFeeRateSchemaMapper.insertBaoliBizFeeRateSchemaBatch(baoliBizFeeRateSchema);
    }

    /**
     * 查询商户费率计划
     * 
     * @param id 商户费率计划主键
     * @return 商户费率计划
     */
    @Override
    public BaoliBizFeeRateSchema selectBaoliBizFeeRateSchemaById(Long id)
    {
        return baoliBizFeeRateSchemaMapper.selectBaoliBizFeeRateSchemaById(id);
    }

    /**
     * 查询商户费率计划列表
     * 
     * @param baoliBizFeeRateSchema 商户费率计划
     * @return 商户费率计划
     */
    @Override
    public List<BaoliBizFeeRateSchema> selectBaoliBizFeeRateSchemaList(BaoliBizFeeRateSchema baoliBizFeeRateSchema)
    {
        return baoliBizFeeRateSchemaMapper.selectBaoliBizFeeRateSchemaList(baoliBizFeeRateSchema);
    }

    /**
     * 新增商户费率计划
     * 
     * @param baoliBizFeeRateSchema 商户费率计划
     * @return 结果
     */
    @Override
    public int insertBaoliBizFeeRateSchema(BaoliBizFeeRateSchema baoliBizFeeRateSchema)
    {
        return baoliBizFeeRateSchemaMapper.insertBaoliBizFeeRateSchema(baoliBizFeeRateSchema);
    }

    /**
     * 修改商户费率计划
     * 
     * @param baoliBizFeeRateSchema 商户费率计划
     * @return 结果
     */
    @Override
    public int updateBaoliBizFeeRateSchema(BaoliBizFeeRateSchema baoliBizFeeRateSchema)
    {
        return baoliBizFeeRateSchemaMapper.updateBaoliBizFeeRateSchema(baoliBizFeeRateSchema);
    }

    /**
     * 批量删除商户费率计划
     * 
     * @param ids 需要删除的商户费率计划主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeRateSchemaByIds(Long[] ids)
    {
        return baoliBizFeeRateSchemaMapper.deleteBaoliBizFeeRateSchemaByIds(ids);
    }


    /**
     * 删除商户费率计划信息
     * 
     * @param id 商户费率计划主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeRateSchemaById(Long id)
    {
        return baoliBizFeeRateSchemaMapper.deleteBaoliBizFeeRateSchemaById(id);
    }
}

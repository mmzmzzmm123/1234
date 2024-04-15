package com.baoli.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.store.mapper.BaoliBizFeeSchemaMapper;
import com.baoli.store.domain.BaoliBizFeeSchema;
import com.baoli.store.service.IBaoliBizFeeSchemaService;

/**
 * 商户费用计划Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-15
 */
@Service
public class BaoliBizFeeSchemaServiceImpl implements IBaoliBizFeeSchemaService 
{
    @Autowired
    private BaoliBizFeeSchemaMapper baoliBizFeeSchemaMapper;

    /**
     * 查询商户费用计划
     * 
     * @param id 商户费用计划主键
     * @return 商户费用计划
     */
    @Override
    public BaoliBizFeeSchema selectBaoliBizFeeSchemaById(Long id)
    {
        return baoliBizFeeSchemaMapper.selectBaoliBizFeeSchemaById(id);
    }

    /**
     * 查询商户费用计划列表
     * 
     * @param baoliBizFeeSchema 商户费用计划
     * @return 商户费用计划
     */
    @Override
    public List<BaoliBizFeeSchema> selectBaoliBizFeeSchemaList(BaoliBizFeeSchema baoliBizFeeSchema)
    {
        return baoliBizFeeSchemaMapper.selectBaoliBizFeeSchemaList(baoliBizFeeSchema);
    }

    /**
     * 新增商户费用计划
     * 
     * @param baoliBizFeeSchema 商户费用计划
     * @return 结果
     */
    @Override
    public int insertBaoliBizFeeSchema(BaoliBizFeeSchema baoliBizFeeSchema)
    {
        return baoliBizFeeSchemaMapper.insertBaoliBizFeeSchema(baoliBizFeeSchema);
    }

    @Override
    public int insertBaoliBizFeeSchemaBatch(List<BaoliBizFeeSchema> baoliBizFeeSchema) {
        return baoliBizFeeSchemaMapper.insertBaoliBizFeeSchemaBatch(baoliBizFeeSchema);
    }

    /**
     * 修改商户费用计划
     * 
     * @param baoliBizFeeSchema 商户费用计划
     * @return 结果
     */
    @Override
    public int updateBaoliBizFeeSchema(BaoliBizFeeSchema baoliBizFeeSchema)
    {
        return baoliBizFeeSchemaMapper.updateBaoliBizFeeSchema(baoliBizFeeSchema);
    }

    /**
     * 批量删除商户费用计划
     * 
     * @param ids 需要删除的商户费用计划主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeSchemaByIds(Long[] ids)
    {
        return baoliBizFeeSchemaMapper.deleteBaoliBizFeeSchemaByIds(ids);
    }

    /**
     * 删除商户费用计划信息
     * 
     * @param id 商户费用计划主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeSchemaById(Long id)
    {
        return baoliBizFeeSchemaMapper.deleteBaoliBizFeeSchemaById(id);
    }
}

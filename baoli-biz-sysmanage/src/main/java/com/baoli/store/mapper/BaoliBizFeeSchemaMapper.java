package com.baoli.store.mapper;

import java.util.List;
import com.baoli.store.domain.BaoliBizFeeSchema;
import org.apache.ibatis.annotations.Param;

/**
 * 商户费用计划Mapper接口
 * 
 * @author niujs
 * @date 2024-04-15
 */
public interface BaoliBizFeeSchemaMapper 
{
    /**
     * 查询商户费用计划
     * 
     * @param id 商户费用计划主键
     * @return 商户费用计划
     */
    public BaoliBizFeeSchema selectBaoliBizFeeSchemaById(Long id);

    /**
     * 查询商户费用计划列表
     * 
     * @param baoliBizFeeSchema 商户费用计划
     * @return 商户费用计划集合
     */
    public List<BaoliBizFeeSchema> selectBaoliBizFeeSchemaList(BaoliBizFeeSchema baoliBizFeeSchema);

    /**
     * 新增商户费用计划
     * 
     * @param baoliBizFeeSchema 商户费用计划
     * @return 结果
     */
    public int insertBaoliBizFeeSchema(BaoliBizFeeSchema baoliBizFeeSchema);
    public int insertBaoliBizFeeSchemaBatch(@Param("baoliBizFeeRateSchema") List<BaoliBizFeeSchema> baoliBizFeeSchema);
    /**
     * 修改商户费用计划
     * 
     * @param baoliBizFeeSchema 商户费用计划
     * @return 结果
     */
    public int updateBaoliBizFeeSchema(BaoliBizFeeSchema baoliBizFeeSchema);

    /**
     * 删除商户费用计划
     * 
     * @param id 商户费用计划主键
     * @return 结果
     */
    public int deleteBaoliBizFeeSchemaById(Long id);

    /**
     * 批量删除商户费用计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizFeeSchemaByIds(Long[] ids);
}

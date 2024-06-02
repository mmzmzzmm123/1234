package com.baoli.store.mapper;

import java.util.List;
import com.baoli.store.domain.BaoliBizFeeRateSchema;
import org.apache.ibatis.annotations.Param;

/**
 * 商户费率计划Mapper接口
 * 
 * @author niujs
 * @date 2024-04-15
 */
public interface BaoliBizFeeRateSchemaMapper 
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

    public int insertBaoliBizFeeRateSchemaBatch(@Param("baoliBizFeeRateSchema") List<BaoliBizFeeRateSchema>  baoliBizFeeRateSchema);

    /**
     * 修改商户费率计划
     * 
     * @param baoliBizFeeRateSchema 商户费率计划
     * @return 结果
     */
    public int updateBaoliBizFeeRateSchema(BaoliBizFeeRateSchema baoliBizFeeRateSchema);

    /**
     * 删除商户费率计划
     * 
     * @param id 商户费率计划主键
     * @return 结果
     */
    public int deleteBaoliBizFeeRateSchemaById(Long id);

    /**
     * 批量删除商户费率计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizFeeRateSchemaByIds(Long[] ids);
}

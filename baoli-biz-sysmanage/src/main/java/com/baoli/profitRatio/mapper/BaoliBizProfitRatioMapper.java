package com.baoli.profitRatio.mapper;

import java.util.List;
import java.util.Map;

import com.baoli.profitRatio.domain.BaoliBizProfitRatio;

/**
 * 利润率Mapper接口
 * 
 * @author niujs
 * @date 2024-05-20
 */
public interface BaoliBizProfitRatioMapper 
{
    /**
     * 查询利润率
     * 
     * @param id 利润率主键
     * @return 利润率
     */
    public Map<String,Object> selectBaoliBizProfitRatioById(Long id);

    /**
     * 查询利润率列表
     * 
     * @param baoliBizProfitRatio 利润率
     * @return 利润率集合
     */
    public List<Map<String,Object>> selectBaoliBizProfitRatioList(BaoliBizProfitRatio baoliBizProfitRatio);

    /**
     * 新增利润率
     * 
     * @param baoliBizProfitRatio 利润率
     * @return 结果
     */
    public int insertBaoliBizProfitRatio(BaoliBizProfitRatio baoliBizProfitRatio);

    /**
     * 修改利润率
     * 
     * @param baoliBizProfitRatio 利润率
     * @return 结果
     */
    public int updateBaoliBizProfitRatio(BaoliBizProfitRatio baoliBizProfitRatio);

    /**
     * 删除利润率
     * 
     * @param id 利润率主键
     * @return 结果
     */
    public int deleteBaoliBizProfitRatioById(Long id);

    /**
     * 批量删除利润率
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizProfitRatioByIds(Long[] ids);
}

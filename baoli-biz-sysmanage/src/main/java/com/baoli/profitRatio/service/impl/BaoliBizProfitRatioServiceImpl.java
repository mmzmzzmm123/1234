package com.baoli.profitRatio.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.profitRatio.mapper.BaoliBizProfitRatioMapper;
import com.baoli.profitRatio.domain.BaoliBizProfitRatio;
import com.baoli.profitRatio.service.IBaoliBizProfitRatioService;

/**
 * 利润率Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-20
 */
@Service
public class BaoliBizProfitRatioServiceImpl implements IBaoliBizProfitRatioService 
{
    @Autowired
    private BaoliBizProfitRatioMapper baoliBizProfitRatioMapper;

    /**
     * 查询利润率
     * 
     * @param id 利润率主键
     * @return 利润率
     */
    @Override
    public Map<String, Object> selectBaoliBizProfitRatioById(Long id)
    {
        return baoliBizProfitRatioMapper.selectBaoliBizProfitRatioById(id);
    }

    /**
     * 查询利润率列表
     * 
     * @param baoliBizProfitRatio 利润率
     * @return 利润率
     */
    @Override
    public List<Map<String,Object>> selectBaoliBizProfitRatioList(BaoliBizProfitRatio baoliBizProfitRatio)
    {
        return baoliBizProfitRatioMapper.selectBaoliBizProfitRatioList(baoliBizProfitRatio);
    }

    /**
     * 新增利润率
     * 
     * @param baoliBizProfitRatio 利润率
     * @return 结果
     */
    @Override
    public int insertBaoliBizProfitRatio(BaoliBizProfitRatio baoliBizProfitRatio)
    {
        return baoliBizProfitRatioMapper.insertBaoliBizProfitRatio(baoliBizProfitRatio);
    }

    /**
     * 修改利润率
     * 
     * @param baoliBizProfitRatio 利润率
     * @return 结果
     */
    @Override
    public int updateBaoliBizProfitRatio(BaoliBizProfitRatio baoliBizProfitRatio)
    {
        return baoliBizProfitRatioMapper.updateBaoliBizProfitRatio(baoliBizProfitRatio);
    }

    /**
     * 批量删除利润率
     * 
     * @param ids 需要删除的利润率主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizProfitRatioByIds(Long[] ids)
    {
        return baoliBizProfitRatioMapper.deleteBaoliBizProfitRatioByIds(ids);
    }

    /**
     * 删除利润率信息
     * 
     * @param id 利润率主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizProfitRatioById(Long id)
    {
        return baoliBizProfitRatioMapper.deleteBaoliBizProfitRatioById(id);
    }
}

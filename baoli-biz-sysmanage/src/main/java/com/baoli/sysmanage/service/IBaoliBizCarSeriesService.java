package com.baoli.sysmanage.service;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizCarSeries;

/**
 * 车系Service接口
 * 
 * @author niujs
 * @date 2024-03-19
 */
public interface IBaoliBizCarSeriesService 
{
    /**
     * 查询车系
     * 
     * @param id 车系主键
     * @return 车系
     */
    public BaoliBizCarSeries selectBaoliBizCarSeriesById(Long id);

    /**
     * 查询车系列表
     * 
     * @param baoliBizCarSeries 车系
     * @return 车系集合
     */
    public List<BaoliBizCarSeries> selectBaoliBizCarSeriesList(BaoliBizCarSeries baoliBizCarSeries);

    /**
     * 新增车系
     * 
     * @param baoliBizCarSeries 车系
     * @return 结果
     */
    public int insertBaoliBizCarSeries(BaoliBizCarSeries baoliBizCarSeries);

    /**
     * 修改车系
     * 
     * @param baoliBizCarSeries 车系
     * @return 结果
     */
    public int updateBaoliBizCarSeries(BaoliBizCarSeries baoliBizCarSeries);

    /**
     * 批量删除车系
     * 
     * @param ids 需要删除的车系主键集合
     * @return 结果
     */
    public int deleteBaoliBizCarSeriesByIds(Long[] ids);

    /**
     * 删除车系信息
     * 
     * @param id 车系主键
     * @return 结果
     */
    public int deleteBaoliBizCarSeriesById(Long id);
}

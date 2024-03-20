package com.baoli.sysmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizCarSeriesMapper;
import com.baoli.sysmanage.domain.BaoliBizCarSeries;
import com.baoli.sysmanage.service.IBaoliBizCarSeriesService;

/**
 * 车系Service业务层处理
 * 
 * @author niujs
 * @date 2024-03-19
 */
@Service
public class BaoliBizCarSeriesServiceImpl implements IBaoliBizCarSeriesService 
{
    @Autowired
    private BaoliBizCarSeriesMapper baoliBizCarSeriesMapper;

    /**
     * 查询车系
     * 
     * @param id 车系主键
     * @return 车系
     */
    @Override
    public BaoliBizCarSeries selectBaoliBizCarSeriesById(Long id)
    {
        return baoliBizCarSeriesMapper.selectBaoliBizCarSeriesById(id);
    }

    /**
     * 查询车系列表
     * 
     * @param baoliBizCarSeries 车系
     * @return 车系
     */
    @Override
    public List<BaoliBizCarSeries> selectBaoliBizCarSeriesList(BaoliBizCarSeries baoliBizCarSeries)
    {
        return baoliBizCarSeriesMapper.selectBaoliBizCarSeriesList(baoliBizCarSeries);
    }

    /**
     * 新增车系
     * 
     * @param baoliBizCarSeries 车系
     * @return 结果
     */
    @Override
    public int insertBaoliBizCarSeries(BaoliBizCarSeries baoliBizCarSeries)
    {
        baoliBizCarSeries.setCreateTime(DateUtils.getNowDate());
        return baoliBizCarSeriesMapper.insertBaoliBizCarSeries(baoliBizCarSeries);
    }

    /**
     * 修改车系
     * 
     * @param baoliBizCarSeries 车系
     * @return 结果
     */
    @Override
    public int updateBaoliBizCarSeries(BaoliBizCarSeries baoliBizCarSeries)
    {
        baoliBizCarSeries.setUpdateTime(DateUtils.getNowDate());
        return baoliBizCarSeriesMapper.updateBaoliBizCarSeries(baoliBizCarSeries);
    }

    /**
     * 批量删除车系
     * 
     * @param ids 需要删除的车系主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarSeriesByIds(Long[] ids)
    {
        return baoliBizCarSeriesMapper.deleteBaoliBizCarSeriesByIds(ids);
    }

    /**
     * 删除车系信息
     * 
     * @param id 车系主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarSeriesById(Long id)
    {
        return baoliBizCarSeriesMapper.deleteBaoliBizCarSeriesById(id);
    }
}

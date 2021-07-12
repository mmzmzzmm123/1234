package com.apollo.usv.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apollo.usv.mapper.CollectorMapper;
import com.apollo.usv.domain.Collector;
import com.apollo.usv.service.ICollectorService;

/**
 * 采集器Service业务层处理
 * 
 * @author lwq
 * @date 2021-07-02
 */
@Service
public class CollectorServiceImpl implements ICollectorService 
{
    @Autowired
    private CollectorMapper collectorMapper;

    /**
     * 查询采集器
     * 
     * @param id 采集器ID
     * @return 采集器
     */
    @Override
    public Collector selectCollectorById(Long id)
    {
        return collectorMapper.selectCollectorById(id);
    }

    /**
     * 查询采集器列表
     * 
     * @param collector 采集器
     * @return 采集器
     */
    @Override
    public List<Collector> selectCollectorList(Collector collector)
    {
        return collectorMapper.selectCollectorList(collector);
    }

    /**
     * 新增采集器
     * 
     * @param collector 采集器
     * @return 结果
     */
    @Override
    public int insertCollector(Collector collector)
    {
        return collectorMapper.insertCollector(collector);
    }

    /**
     * 修改采集器
     * 
     * @param collector 采集器
     * @return 结果
     */
    @Override
    public int updateCollector(Collector collector)
    {
        return collectorMapper.updateCollector(collector);
    }

    /**
     * 批量删除采集器
     * 
     * @param ids 需要删除的采集器ID
     * @return 结果
     */
    @Override
    public int deleteCollectorByIds(Long[] ids)
    {
        return collectorMapper.deleteCollectorByIds(ids);
    }

    /**
     * 删除采集器信息
     * 
     * @param id 采集器ID
     * @return 结果
     */
    @Override
    public int deleteCollectorById(Long id)
    {
        return collectorMapper.deleteCollectorById(id);
    }
}

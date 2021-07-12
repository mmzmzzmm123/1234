package com.apollo.usv.mapper;

import java.util.List;
import com.apollo.usv.domain.Collector;

/**
 * 采集器Mapper接口
 * 
 * @author lwq
 * @date 2021-07-02
 */
public interface CollectorMapper 
{
    /**
     * 查询采集器
     * 
     * @param id 采集器ID
     * @return 采集器
     */
    public Collector selectCollectorById(Long id);

    /**
     * 查询采集器列表
     * 
     * @param collector 采集器
     * @return 采集器集合
     */
    public List<Collector> selectCollectorList(Collector collector);

    /**
     * 新增采集器
     * 
     * @param collector 采集器
     * @return 结果
     */
    public int insertCollector(Collector collector);

    /**
     * 修改采集器
     * 
     * @param collector 采集器
     * @return 结果
     */
    public int updateCollector(Collector collector);

    /**
     * 删除采集器
     * 
     * @param id 采集器ID
     * @return 结果
     */
    public int deleteCollectorById(Long id);

    /**
     * 批量删除采集器
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCollectorByIds(Long[] ids);
}

package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BusArbitrations;

/**
 * 仲裁Service接口
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
public interface IBusArbitrationsService 
{
    /**
     * 查询仲裁
     * 
     * @param arbitrationId 仲裁主键
     * @return 仲裁
     */
    public BusArbitrations selectBusArbitrationsByArbitrationId(Long arbitrationId);

    /**
     * 查询仲裁列表
     * 
     * @param busArbitrations 仲裁
     * @return 仲裁集合
     */
    public List<BusArbitrations> selectBusArbitrationsList(BusArbitrations busArbitrations);

    /**
     * 新增仲裁
     * 
     * @param busArbitrations 仲裁
     * @return 结果
     */
    public int insertBusArbitrations(BusArbitrations busArbitrations);

    /**
     * 修改仲裁
     * 
     * @param busArbitrations 仲裁
     * @return 结果
     */
    public int updateBusArbitrations(BusArbitrations busArbitrations);

    /**
     * 批量删除仲裁
     * 
     * @param arbitrationIds 需要删除的仲裁主键集合
     * @return 结果
     */
    public int deleteBusArbitrationsByArbitrationIds(Long[] arbitrationIds);

    /**
     * 删除仲裁信息
     * 
     * @param arbitrationId 仲裁主键
     * @return 结果
     */
    public int deleteBusArbitrationsByArbitrationId(Long arbitrationId);
}

package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BusArbitrations;

/**
 * 仲裁Mapper接口
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
public interface BusArbitrationsMapper 
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
     * 删除仲裁
     * //deleteBusArbitrationByArbitrationId
     * @param arbitrationId 仲裁主键
     * @return 结果
     */
    public int deleteBusArbitrationsByArbitrationId(Long arbitrationId);

    /**
     * 批量删除仲裁
     * 
     * @param arbitrationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusArbitrationsByArbitrationIds(Long[] arbitrationIds);
}

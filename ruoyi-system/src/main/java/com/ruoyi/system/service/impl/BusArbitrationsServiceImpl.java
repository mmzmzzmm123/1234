package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BusArbitrationsMapper;
import com.ruoyi.system.domain.BusArbitrations;
import com.ruoyi.system.service.IBusArbitrationsService;

/**
 * 仲裁Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
@Service
public class BusArbitrationsServiceImpl implements IBusArbitrationsService 
{
    @Autowired
    private BusArbitrationsMapper busArbitrationsMapper;

    /**
     * 查询仲裁
     * 
     * @param arbitrationId 仲裁主键
     * @return 仲裁
     */
    @Override
    public BusArbitrations selectBusArbitrationsByArbitrationId(Long arbitrationId)
    {
        return busArbitrationsMapper.selectBusArbitrationsByArbitrationId(arbitrationId);
    }

    /**
     * 查询仲裁列表
     * 
     * @param busArbitrations 仲裁
     * @return 仲裁
     */
    @Override
    public List<BusArbitrations> selectBusArbitrationsList(BusArbitrations busArbitrations)
    {
        return busArbitrationsMapper.selectBusArbitrationsList(busArbitrations);
    }

    /**
     * 新增仲裁
     * 
     * @param busArbitrations 仲裁
     * @return 结果
     */
    @Override
    public int insertBusArbitrations(BusArbitrations busArbitrations)
    {
        return busArbitrationsMapper.insertBusArbitrations(busArbitrations);
    }

    /**
     * 修改仲裁
     * 
     * @param busArbitrations 仲裁
     * @return 结果
     */
    @Override
    public int updateBusArbitrations(BusArbitrations busArbitrations)
    {
        return busArbitrationsMapper.updateBusArbitrations(busArbitrations);
    }

    /**
     * 批量删除仲裁
     * 
     * @param arbitrationIds 需要删除的仲裁主键
     * @return 结果
     */
    @Override
    public int deleteBusArbitrationsByArbitrationIds(Long[] arbitrationIds)
    {
        return busArbitrationsMapper.deleteBusArbitrationsByArbitrationIds(arbitrationIds);
    }

    /**
     * 删除仲裁信息
     * 
     * @param arbitrationId 仲裁主键
     * @return 结果
     */
    @Override
    public int deleteBusArbitrationsByArbitrationId(Long arbitrationId)
    {
        return busArbitrationsMapper.deleteBusArbitrationsByArbitrationId(arbitrationId);
    }
}

package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehicleSeriesMapper;
import com.ruoyi.system.domain.VehicleSeries;
import com.ruoyi.system.service.IVehicleSeriesService;

/**
 * 车系管理Service业务层处理
 * 
 * @author carol
 * @date 2024-03-01
 */
@Service
public class VehicleSeriesServiceImpl implements IVehicleSeriesService 
{
    @Autowired
    private VehicleSeriesMapper vehicleSeriesMapper;

    /**
     * 查询车系管理
     * 
     * @param id 车系管理主键
     * @return 车系管理
     */
    @Override
    public VehicleSeries selectVehicleSeriesById(Long id)
    {
        return vehicleSeriesMapper.selectVehicleSeriesById(id);
    }

    /**
     * 查询车系管理列表
     * 
     * @param vehicleSeries 车系管理
     * @return 车系管理
     */
    @Override
    public List<VehicleSeries> selectVehicleSeriesList(VehicleSeries vehicleSeries)
    {
        return vehicleSeriesMapper.selectVehicleSeriesList(vehicleSeries);
    }

    /**
     * 新增车系管理
     * 
     * @param vehicleSeries 车系管理
     * @return 结果
     */
    @Override
    public int insertVehicleSeries(VehicleSeries vehicleSeries)
    {
        vehicleSeries.setCreateTime(DateUtils.getNowDate());
        return vehicleSeriesMapper.insertVehicleSeries(vehicleSeries);
    }

    /**
     * 修改车系管理
     * 
     * @param vehicleSeries 车系管理
     * @return 结果
     */
    @Override
    public int updateVehicleSeries(VehicleSeries vehicleSeries)
    {
        vehicleSeries.setUpdateTime(DateUtils.getNowDate());
        return vehicleSeriesMapper.updateVehicleSeries(vehicleSeries);
    }

    /**
     * 批量删除车系管理
     * 
     * @param ids 需要删除的车系管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSeriesByIds(Long[] ids)
    {
        return vehicleSeriesMapper.deleteVehicleSeriesByIds(ids);
    }

    /**
     * 删除车系管理信息
     * 
     * @param id 车系管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSeriesById(Long id)
    {
        return vehicleSeriesMapper.deleteVehicleSeriesById(id);
    }
}

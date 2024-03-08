package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VehicleSeries;

/**
 * 车系管理Mapper接口
 * 
 * @author carol
 * @date 2024-03-01
 */
public interface VehicleSeriesMapper 
{
    /**
     * 查询车系管理
     * 
     * @param id 车系管理主键
     * @return 车系管理
     */
    public VehicleSeries selectVehicleSeriesById(Long id);

    /**
     * 查询车系管理列表
     * 
     * @param vehicleSeries 车系管理
     * @return 车系管理集合
     */
    public List<VehicleSeries> selectVehicleSeriesList(VehicleSeries vehicleSeries);

    /**
     * 新增车系管理
     * 
     * @param vehicleSeries 车系管理
     * @return 结果
     */
    public int insertVehicleSeries(VehicleSeries vehicleSeries);

    /**
     * 修改车系管理
     * 
     * @param vehicleSeries 车系管理
     * @return 结果
     */
    public int updateVehicleSeries(VehicleSeries vehicleSeries);

    /**
     * 删除车系管理
     * 
     * @param id 车系管理主键
     * @return 结果
     */
    public int deleteVehicleSeriesById(Long id);

    /**
     * 批量删除车系管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVehicleSeriesByIds(Long[] ids);
}

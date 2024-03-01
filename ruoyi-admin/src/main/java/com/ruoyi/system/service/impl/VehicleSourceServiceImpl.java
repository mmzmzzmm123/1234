package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehicleSourceMapper;
import com.ruoyi.system.domain.VehicleSource;
import com.ruoyi.system.service.IVehicleSourceService;

/**
 * 车源管理Service业务层处理
 * 
 * @author carol
 * @date 2024-03-01
 */
@Service
public class VehicleSourceServiceImpl implements IVehicleSourceService 
{
    @Autowired
    private VehicleSourceMapper vehicleSourceMapper;

    /**
     * 查询车源管理
     * 
     * @param id 车源管理主键
     * @return 车源管理
     */
    @Override
    public VehicleSource selectVehicleSourceById(Long id)
    {
        return vehicleSourceMapper.selectVehicleSourceById(id);
    }

    /**
     * 查询车源管理列表
     * 
     * @param vehicleSource 车源管理
     * @return 车源管理
     */
    @Override
    public List<VehicleSource> selectVehicleSourceList(VehicleSource vehicleSource)
    {
        return vehicleSourceMapper.selectVehicleSourceList(vehicleSource);
    }

    /**
     * 新增车源管理
     * 
     * @param vehicleSource 车源管理
     * @return 结果
     */
    @Override
    public int insertVehicleSource(VehicleSource vehicleSource)
    {
        vehicleSource.setCreateTime(DateUtils.getNowDate());
        return vehicleSourceMapper.insertVehicleSource(vehicleSource);
    }

    /**
     * 修改车源管理
     * 
     * @param vehicleSource 车源管理
     * @return 结果
     */
    @Override
    public int updateVehicleSource(VehicleSource vehicleSource)
    {
        vehicleSource.setUpdateTime(DateUtils.getNowDate());
        return vehicleSourceMapper.updateVehicleSource(vehicleSource);
    }

    /**
     * 批量删除车源管理
     * 
     * @param ids 需要删除的车源管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSourceByIds(Long[] ids)
    {
        return vehicleSourceMapper.deleteVehicleSourceByIds(ids);
    }

    /**
     * 删除车源管理信息
     * 
     * @param id 车源管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSourceById(Long id)
    {
        return vehicleSourceMapper.deleteVehicleSourceById(id);
    }
}

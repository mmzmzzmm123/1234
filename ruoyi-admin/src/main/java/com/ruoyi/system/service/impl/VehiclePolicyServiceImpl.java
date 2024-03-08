package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehiclePolicyMapper;
import com.ruoyi.system.domain.VehiclePolicy;
import com.ruoyi.system.service.IVehiclePolicyService;

/**
 * 政策信息管理Service业务层处理
 * 
 * @author carol
 * @date 2024-03-01
 */
@Service
public class VehiclePolicyServiceImpl implements IVehiclePolicyService 
{
    @Autowired
    private VehiclePolicyMapper vehiclePolicyMapper;

    /**
     * 查询政策信息管理
     * 
     * @param id 政策信息管理主键
     * @return 政策信息管理
     */
    @Override
    public VehiclePolicy selectVehiclePolicyById(Long id)
    {
        return vehiclePolicyMapper.selectVehiclePolicyById(id);
    }

    /**
     * 查询政策信息管理列表
     * 
     * @param vehiclePolicy 政策信息管理
     * @return 政策信息管理
     */
    @Override
    public List<VehiclePolicy> selectVehiclePolicyList(VehiclePolicy vehiclePolicy)
    {
        return vehiclePolicyMapper.selectVehiclePolicyList(vehiclePolicy);
    }

    /**
     * 新增政策信息管理
     * 
     * @param vehiclePolicy 政策信息管理
     * @return 结果
     */
    @Override
    public int insertVehiclePolicy(VehiclePolicy vehiclePolicy)
    {
        vehiclePolicy.setCreateTime(DateUtils.getNowDate());
        return vehiclePolicyMapper.insertVehiclePolicy(vehiclePolicy);
    }

    /**
     * 修改政策信息管理
     * 
     * @param vehiclePolicy 政策信息管理
     * @return 结果
     */
    @Override
    public int updateVehiclePolicy(VehiclePolicy vehiclePolicy)
    {
        vehiclePolicy.setUpdateTime(DateUtils.getNowDate());
        return vehiclePolicyMapper.updateVehiclePolicy(vehiclePolicy);
    }

    /**
     * 批量删除政策信息管理
     * 
     * @param ids 需要删除的政策信息管理主键
     * @return 结果
     */
    @Override
    public int deleteVehiclePolicyByIds(Long[] ids)
    {
        return vehiclePolicyMapper.deleteVehiclePolicyByIds(ids);
    }

    /**
     * 删除政策信息管理信息
     * 
     * @param id 政策信息管理主键
     * @return 结果
     */
    @Override
    public int deleteVehiclePolicyById(Long id)
    {
        return vehiclePolicyMapper.deleteVehiclePolicyById(id);
    }
}

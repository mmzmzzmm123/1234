package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehiclePolicyMapper;
import com.ruoyi.system.domain.VehiclePolicy;
import com.ruoyi.system.service.IVehiclePolicyService;

/**
 * 出口国家政策Service业务层处理
 * 
 * @author carol
 * @date 2024-02-29
 */
@Service
public class VehiclePolicyServiceImpl implements IVehiclePolicyService 
{
    @Autowired
    private VehiclePolicyMapper vehiclePolicyMapper;

    /**
     * 查询出口国家政策
     * 
     * @param id 出口国家政策主键
     * @return 出口国家政策
     */
    @Override
    public VehiclePolicy selectVehiclePolicyById(Long id)
    {
        return vehiclePolicyMapper.selectVehiclePolicyById(id);
    }

    /**
     * 查询出口国家政策列表
     * 
     * @param vehiclePolicy 出口国家政策
     * @return 出口国家政策
     */
    @Override
    public List<VehiclePolicy> selectVehiclePolicyList(VehiclePolicy vehiclePolicy)
    {
        return vehiclePolicyMapper.selectVehiclePolicyList(vehiclePolicy);
    }

    /**
     * 新增出口国家政策
     * 
     * @param vehiclePolicy 出口国家政策
     * @return 结果
     */
    @Override
    public int insertVehiclePolicy(VehiclePolicy vehiclePolicy)
    {
        vehiclePolicy.setCreateTime(DateUtils.getNowDate());
        return vehiclePolicyMapper.insertVehiclePolicy(vehiclePolicy);
    }

    /**
     * 修改出口国家政策
     * 
     * @param vehiclePolicy 出口国家政策
     * @return 结果
     */
    @Override
    public int updateVehiclePolicy(VehiclePolicy vehiclePolicy)
    {
        vehiclePolicy.setUpdateTime(DateUtils.getNowDate());
        return vehiclePolicyMapper.updateVehiclePolicy(vehiclePolicy);
    }

    /**
     * 批量删除出口国家政策
     * 
     * @param ids 需要删除的出口国家政策主键
     * @return 结果
     */
    @Override
    public int deleteVehiclePolicyByIds(Long[] ids)
    {
        return vehiclePolicyMapper.deleteVehiclePolicyByIds(ids);
    }

    /**
     * 删除出口国家政策信息
     * 
     * @param id 出口国家政策主键
     * @return 结果
     */
    @Override
    public int deleteVehiclePolicyById(Long id)
    {
        return vehiclePolicyMapper.deleteVehiclePolicyById(id);
    }
}

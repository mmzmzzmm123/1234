package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VehiclePolicy;

/**
 * 政策信息管理Service接口
 * 
 * @author carol
 * @date 2024-03-01
 */
public interface IVehiclePolicyService 
{
    /**
     * 查询政策信息管理
     * 
     * @param id 政策信息管理主键
     * @return 政策信息管理
     */
    public VehiclePolicy selectVehiclePolicyById(Long id);

    /**
     * 查询政策信息管理列表
     * 
     * @param vehiclePolicy 政策信息管理
     * @return 政策信息管理集合
     */
    public List<VehiclePolicy> selectVehiclePolicyList(VehiclePolicy vehiclePolicy);

    /**
     * 新增政策信息管理
     * 
     * @param vehiclePolicy 政策信息管理
     * @return 结果
     */
    public int insertVehiclePolicy(VehiclePolicy vehiclePolicy);

    /**
     * 修改政策信息管理
     * 
     * @param vehiclePolicy 政策信息管理
     * @return 结果
     */
    public int updateVehiclePolicy(VehiclePolicy vehiclePolicy);

    /**
     * 批量删除政策信息管理
     * 
     * @param ids 需要删除的政策信息管理主键集合
     * @return 结果
     */
    public int deleteVehiclePolicyByIds(Long[] ids);

    /**
     * 删除政策信息管理信息
     * 
     * @param id 政策信息管理主键
     * @return 结果
     */
    public int deleteVehiclePolicyById(Long id);
}

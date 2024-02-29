package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VehicleUserinfo;

/**
 * 用户展示信息Mapper接口
 * 
 * @author carol
 * @date 2024-02-29
 */
public interface VehicleUserinfoMapper 
{
    /**
     * 查询用户展示信息
     * 
     * @param id 用户展示信息主键
     * @return 用户展示信息
     */
    public VehicleUserinfo selectVehicleUserinfoById(Long id);

    /**
     * 查询用户展示信息列表
     * 
     * @param vehicleUserinfo 用户展示信息
     * @return 用户展示信息集合
     */
    public List<VehicleUserinfo> selectVehicleUserinfoList(VehicleUserinfo vehicleUserinfo);

    /**
     * 新增用户展示信息
     * 
     * @param vehicleUserinfo 用户展示信息
     * @return 结果
     */
    public int insertVehicleUserinfo(VehicleUserinfo vehicleUserinfo);

    /**
     * 修改用户展示信息
     * 
     * @param vehicleUserinfo 用户展示信息
     * @return 结果
     */
    public int updateVehicleUserinfo(VehicleUserinfo vehicleUserinfo);

    /**
     * 删除用户展示信息
     * 
     * @param id 用户展示信息主键
     * @return 结果
     */
    public int deleteVehicleUserinfoById(Long id);

    /**
     * 批量删除用户展示信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVehicleUserinfoByIds(Long[] ids);
}

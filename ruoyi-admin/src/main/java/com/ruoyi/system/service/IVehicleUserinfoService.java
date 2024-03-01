package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VehicleUserinfo;

/**
 * 小程序用户信息管理Service接口
 * 
 * @author carol
 * @date 2024-03-01
 */
public interface IVehicleUserinfoService 
{
    /**
     * 查询小程序用户信息管理
     * 
     * @param id 小程序用户信息管理主键
     * @return 小程序用户信息管理
     */
    public VehicleUserinfo selectVehicleUserinfoById(Long id);

    /**
     * 查询小程序用户信息管理列表
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 小程序用户信息管理集合
     */
    public List<VehicleUserinfo> selectVehicleUserinfoList(VehicleUserinfo vehicleUserinfo);

    /**
     * 新增小程序用户信息管理
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 结果
     */
    public int insertVehicleUserinfo(VehicleUserinfo vehicleUserinfo);

    /**
     * 修改小程序用户信息管理
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 结果
     */
    public int updateVehicleUserinfo(VehicleUserinfo vehicleUserinfo);

    /**
     * 批量删除小程序用户信息管理
     * 
     * @param ids 需要删除的小程序用户信息管理主键集合
     * @return 结果
     */
    public int deleteVehicleUserinfoByIds(Long[] ids);

    /**
     * 删除小程序用户信息管理信息
     * 
     * @param id 小程序用户信息管理主键
     * @return 结果
     */
    public int deleteVehicleUserinfoById(Long id);
}

package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehicleUserinfoMapper;
import com.ruoyi.system.domain.VehicleUserinfo;
import com.ruoyi.system.service.IVehicleUserinfoService;

/**
 * 小程序用户信息管理Service业务层处理
 * 
 * @author carol
 * @date 2024-03-01
 */
@Service
public class VehicleUserinfoServiceImpl implements IVehicleUserinfoService 
{
    @Autowired
    private VehicleUserinfoMapper vehicleUserinfoMapper;

    /**
     * 查询小程序用户信息管理
     * 
     * @param id 小程序用户信息管理主键
     * @return 小程序用户信息管理
     */
    @Override
    public VehicleUserinfo selectVehicleUserinfoById(Long id)
    {
        return vehicleUserinfoMapper.selectVehicleUserinfoById(id);
    }

    /**
     * 查询小程序用户信息管理列表
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 小程序用户信息管理
     */
    @Override
    public List<VehicleUserinfo> selectVehicleUserinfoList(VehicleUserinfo vehicleUserinfo)
    {
        return vehicleUserinfoMapper.selectVehicleUserinfoList(vehicleUserinfo);
    }

    /**
     * 新增小程序用户信息管理
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 结果
     */
    @Override
    public int insertVehicleUserinfo(VehicleUserinfo vehicleUserinfo)
    {
        vehicleUserinfo.setCreateTime(DateUtils.getNowDate());
        return vehicleUserinfoMapper.insertVehicleUserinfo(vehicleUserinfo);
    }

    /**
     * 修改小程序用户信息管理
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 结果
     */
    @Override
    public int updateVehicleUserinfo(VehicleUserinfo vehicleUserinfo)
    {
        vehicleUserinfo.setUpdateTime(DateUtils.getNowDate());
        return vehicleUserinfoMapper.updateVehicleUserinfo(vehicleUserinfo);
    }

    /**
     * 批量删除小程序用户信息管理
     * 
     * @param ids 需要删除的小程序用户信息管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleUserinfoByIds(Long[] ids)
    {
        return vehicleUserinfoMapper.deleteVehicleUserinfoByIds(ids);
    }

    /**
     * 删除小程序用户信息管理信息
     * 
     * @param id 小程序用户信息管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleUserinfoById(Long id)
    {
        return vehicleUserinfoMapper.deleteVehicleUserinfoById(id);
    }
}

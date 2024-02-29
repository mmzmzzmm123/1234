package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehicleUserinfoMapper;
import com.ruoyi.system.domain.VehicleUserinfo;
import com.ruoyi.system.service.IVehicleUserinfoService;

/**
 * 用户展示信息Service业务层处理
 * 
 * @author carol
 * @date 2024-02-29
 */
@Service
public class VehicleUserinfoServiceImpl implements IVehicleUserinfoService 
{
    @Autowired
    private VehicleUserinfoMapper vehicleUserinfoMapper;

    /**
     * 查询用户展示信息
     * 
     * @param id 用户展示信息主键
     * @return 用户展示信息
     */
    @Override
    public VehicleUserinfo selectVehicleUserinfoById(Long id)
    {
        return vehicleUserinfoMapper.selectVehicleUserinfoById(id);
    }

    /**
     * 查询用户展示信息列表
     * 
     * @param vehicleUserinfo 用户展示信息
     * @return 用户展示信息
     */
    @Override
    public List<VehicleUserinfo> selectVehicleUserinfoList(VehicleUserinfo vehicleUserinfo)
    {
        return vehicleUserinfoMapper.selectVehicleUserinfoList(vehicleUserinfo);
    }

    /**
     * 新增用户展示信息
     * 
     * @param vehicleUserinfo 用户展示信息
     * @return 结果
     */
    @Override
    public int insertVehicleUserinfo(VehicleUserinfo vehicleUserinfo)
    {
        vehicleUserinfo.setCreateTime(DateUtils.getNowDate());
        return vehicleUserinfoMapper.insertVehicleUserinfo(vehicleUserinfo);
    }

    /**
     * 修改用户展示信息
     * 
     * @param vehicleUserinfo 用户展示信息
     * @return 结果
     */
    @Override
    public int updateVehicleUserinfo(VehicleUserinfo vehicleUserinfo)
    {
        vehicleUserinfo.setUpdateTime(DateUtils.getNowDate());
        return vehicleUserinfoMapper.updateVehicleUserinfo(vehicleUserinfo);
    }

    /**
     * 批量删除用户展示信息
     * 
     * @param ids 需要删除的用户展示信息主键
     * @return 结果
     */
    @Override
    public int deleteVehicleUserinfoByIds(Long[] ids)
    {
        return vehicleUserinfoMapper.deleteVehicleUserinfoByIds(ids);
    }

    /**
     * 删除用户展示信息信息
     * 
     * @param id 用户展示信息主键
     * @return 结果
     */
    @Override
    public int deleteVehicleUserinfoById(Long id)
    {
        return vehicleUserinfoMapper.deleteVehicleUserinfoById(id);
    }
}

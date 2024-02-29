package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehicleBrandMapper;
import com.ruoyi.system.domain.VehicleBrand;
import com.ruoyi.system.service.IVehicleBrandService;

/**
 * 汽车品牌管理Service业务层处理
 * 
 * @author carol
 * @date 2024-02-29
 */
@Service
public class VehicleBrandServiceImpl implements IVehicleBrandService 
{
    @Autowired
    private VehicleBrandMapper vehicleBrandMapper;

    /**
     * 查询汽车品牌管理
     * 
     * @param id 汽车品牌管理主键
     * @return 汽车品牌管理
     */
    @Override
    public VehicleBrand selectVehicleBrandById(Long id)
    {
        return vehicleBrandMapper.selectVehicleBrandById(id);
    }

    /**
     * 查询汽车品牌管理列表
     * 
     * @param vehicleBrand 汽车品牌管理
     * @return 汽车品牌管理
     */
    @Override
    public List<VehicleBrand> selectVehicleBrandList(VehicleBrand vehicleBrand)
    {
        return vehicleBrandMapper.selectVehicleBrandList(vehicleBrand);
    }

    /**
     * 新增汽车品牌管理
     * 
     * @param vehicleBrand 汽车品牌管理
     * @return 结果
     */
    @Override
    public int insertVehicleBrand(VehicleBrand vehicleBrand)
    {
        vehicleBrand.setCreateTime(DateUtils.getNowDate());
        return vehicleBrandMapper.insertVehicleBrand(vehicleBrand);
    }

    /**
     * 修改汽车品牌管理
     * 
     * @param vehicleBrand 汽车品牌管理
     * @return 结果
     */
    @Override
    public int updateVehicleBrand(VehicleBrand vehicleBrand)
    {
        vehicleBrand.setUpdateTime(DateUtils.getNowDate());
        return vehicleBrandMapper.updateVehicleBrand(vehicleBrand);
    }

    /**
     * 批量删除汽车品牌管理
     * 
     * @param ids 需要删除的汽车品牌管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleBrandByIds(Long[] ids)
    {
        return vehicleBrandMapper.deleteVehicleBrandByIds(ids);
    }

    /**
     * 删除汽车品牌管理信息
     * 
     * @param id 汽车品牌管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleBrandById(Long id)
    {
        return vehicleBrandMapper.deleteVehicleBrandById(id);
    }
}

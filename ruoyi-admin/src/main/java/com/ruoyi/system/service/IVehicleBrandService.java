package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VehicleBrand;

/**
 * 汽车品牌管理Service接口
 * 
 * @author carol
 * @date 2024-02-29
 */
public interface IVehicleBrandService 
{
    /**
     * 查询汽车品牌管理
     * 
     * @param id 汽车品牌管理主键
     * @return 汽车品牌管理
     */
    public VehicleBrand selectVehicleBrandById(Long id);

    /**
     * 查询汽车品牌管理列表
     * 
     * @param vehicleBrand 汽车品牌管理
     * @return 汽车品牌管理集合
     */
    public List<VehicleBrand> selectVehicleBrandList(VehicleBrand vehicleBrand);

    /**
     * 新增汽车品牌管理
     * 
     * @param vehicleBrand 汽车品牌管理
     * @return 结果
     */
    public int insertVehicleBrand(VehicleBrand vehicleBrand);

    /**
     * 修改汽车品牌管理
     * 
     * @param vehicleBrand 汽车品牌管理
     * @return 结果
     */
    public int updateVehicleBrand(VehicleBrand vehicleBrand);

    /**
     * 批量删除汽车品牌管理
     * 
     * @param ids 需要删除的汽车品牌管理主键集合
     * @return 结果
     */
    public int deleteVehicleBrandByIds(Long[] ids);

    /**
     * 删除汽车品牌管理信息
     * 
     * @param id 汽车品牌管理主键
     * @return 结果
     */
    public int deleteVehicleBrandById(Long id);
}

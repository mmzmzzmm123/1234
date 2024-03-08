package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VehicleSourceImg;

/**
 * 车源图片管理Service接口
 * 
 * @author carol
 * @date 2024-03-01
 */
public interface IVehicleSourceImgService 
{
    /**
     * 查询车源图片管理
     * 
     * @param id 车源图片管理主键
     * @return 车源图片管理
     */
    public VehicleSourceImg selectVehicleSourceImgById(Long id);

    /**
     * 查询车源图片管理列表
     * 
     * @param vehicleSourceImg 车源图片管理
     * @return 车源图片管理集合
     */
    public List<VehicleSourceImg> selectVehicleSourceImgList(VehicleSourceImg vehicleSourceImg);

    /**
     * 新增车源图片管理
     * 
     * @param vehicleSourceImg 车源图片管理
     * @return 结果
     */
    public int insertVehicleSourceImg(VehicleSourceImg vehicleSourceImg);

    /**
     * 修改车源图片管理
     * 
     * @param vehicleSourceImg 车源图片管理
     * @return 结果
     */
    public int updateVehicleSourceImg(VehicleSourceImg vehicleSourceImg);

    /**
     * 批量删除车源图片管理
     * 
     * @param ids 需要删除的车源图片管理主键集合
     * @return 结果
     */
    public int deleteVehicleSourceImgByIds(Long[] ids);

    /**
     * 删除车源图片管理信息
     * 
     * @param id 车源图片管理主键
     * @return 结果
     */
    public int deleteVehicleSourceImgById(Long id);
}

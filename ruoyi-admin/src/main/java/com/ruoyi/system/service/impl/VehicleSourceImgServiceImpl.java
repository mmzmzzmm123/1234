package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehicleSourceImgMapper;
import com.ruoyi.system.domain.VehicleSourceImg;
import com.ruoyi.system.service.IVehicleSourceImgService;

/**
 * 车源图片管理Service业务层处理
 * 
 * @author carol
 * @date 2024-03-01
 */
@Service
public class VehicleSourceImgServiceImpl implements IVehicleSourceImgService 
{
    @Autowired
    private VehicleSourceImgMapper vehicleSourceImgMapper;

    /**
     * 查询车源图片管理
     * 
     * @param id 车源图片管理主键
     * @return 车源图片管理
     */
    @Override
    public VehicleSourceImg selectVehicleSourceImgById(Long id)
    {
        return vehicleSourceImgMapper.selectVehicleSourceImgById(id);
    }

    /**
     * 查询车源图片管理列表
     * 
     * @param vehicleSourceImg 车源图片管理
     * @return 车源图片管理
     */
    @Override
    public List<VehicleSourceImg> selectVehicleSourceImgList(VehicleSourceImg vehicleSourceImg)
    {
        return vehicleSourceImgMapper.selectVehicleSourceImgList(vehicleSourceImg);
    }

    /**
     * 新增车源图片管理
     * 
     * @param vehicleSourceImg 车源图片管理
     * @return 结果
     */
    @Override
    public int insertVehicleSourceImg(VehicleSourceImg vehicleSourceImg)
    {
        vehicleSourceImg.setCreateTime(DateUtils.getNowDate());
        return vehicleSourceImgMapper.insertVehicleSourceImg(vehicleSourceImg);
    }

    /**
     * 修改车源图片管理
     * 
     * @param vehicleSourceImg 车源图片管理
     * @return 结果
     */
    @Override
    public int updateVehicleSourceImg(VehicleSourceImg vehicleSourceImg)
    {
        vehicleSourceImg.setUpdateTime(DateUtils.getNowDate());
        return vehicleSourceImgMapper.updateVehicleSourceImg(vehicleSourceImg);
    }

    /**
     * 批量删除车源图片管理
     * 
     * @param ids 需要删除的车源图片管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSourceImgByIds(Long[] ids)
    {
        return vehicleSourceImgMapper.deleteVehicleSourceImgByIds(ids);
    }

    /**
     * 删除车源图片管理信息
     * 
     * @param id 车源图片管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSourceImgById(Long id)
    {
        return vehicleSourceImgMapper.deleteVehicleSourceImgById(id);
    }
}

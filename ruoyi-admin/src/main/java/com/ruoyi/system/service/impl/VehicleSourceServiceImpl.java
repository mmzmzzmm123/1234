package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.VehicleBrand;
import com.ruoyi.system.domain.VehicleSeries;
import com.ruoyi.system.domain.VehicleUserinfo;
import com.ruoyi.system.mapper.VehicleSeriesMapper;
import com.ruoyi.system.mapper.VehicleSourceImgMapper;
import com.ruoyi.system.mapper.VehicleUserinfoMapper;
import com.ruoyi.system.vo.VehicleSourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VehicleSourceMapper;
import com.ruoyi.system.domain.VehicleSource;
import com.ruoyi.system.service.IVehicleSourceService;

/**
 * 车源管理Service业务层处理
 *
 * @author carol
 * @date 2024-03-01
 */
@Service
public class VehicleSourceServiceImpl implements IVehicleSourceService
{
    @Autowired
    private VehicleSourceMapper vehicleSourceMapper;

    @Autowired
    private VehicleSeriesMapper vehicleSeriesMapper;

    @Autowired
    private VehicleUserinfoMapper userinfoMapper;

    @Autowired
    private VehicleSourceImgMapper sourceImgMapper;


    /**
     * 查询车源管理
     *
     * @param id 车源管理主键
     * @return 车源管理
     */
    @Override
    public VehicleSource selectVehicleSourceById(Long id)
    {
        return vehicleSourceMapper.selectVehicleSourceById(id);
    }

    /**
     * 查询车源管理列表
     *
     * @param vehicleSource 车源管理
     * @return 车源管理
     */
    @Override
    public List<VehicleSource> selectVehicleSourceList(VehicleSource vehicleSource)
    {
        return vehicleSourceMapper.selectVehicleSourceList(vehicleSource);
    }

    /**
     * 新增车源管理
     *
     * @param vehicleSource 车源管理
     * @return 结果
     */
    @Override
    public int insertVehicleSource(VehicleSource vehicleSource)
    {
        vehicleSource.setCreateTime(DateUtils.getNowDate());
        return vehicleSourceMapper.insertVehicleSource(vehicleSource);
    }

    /**
     * 修改车源管理
     *
     * @param vehicleSource 车源管理
     * @return 结果
     */
    @Override
    public int updateVehicleSource(VehicleSource vehicleSource)
    {
        vehicleSource.setUpdateTime(DateUtils.getNowDate());
        return vehicleSourceMapper.updateVehicleSource(vehicleSource);
    }

    /**
     * 批量删除车源管理
     *
     * @param ids 需要删除的车源管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSourceByIds(Long[] ids)
    {
        return vehicleSourceMapper.deleteVehicleSourceByIds(ids);
    }

    /**
     * 删除车源管理信息
     *
     * @param id 车源管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleSourceById(Long id)
    {
        return vehicleSourceMapper.deleteVehicleSourceById(id);
    }

    @Override
    public List<VehicleBrand> listGroupByBrandId() {
        //以groupId分组查询车源中有哪些品牌id
        List<Long> brandIds = vehicleSourceMapper.listGroupByBrandId();
        //用品牌id查询品牌进行返回
        brandIds.forEach(e-> System.out.println(e));

        return null;
    }

    @Override
    public R getInfoById(Long id) {
        //前端返回对象
        VehicleSourceVO vehicleSourceVO = new VehicleSourceVO();
        //查询车源
        VehicleSource vehicleSource = vehicleSourceMapper.selectVehicleSourceById(id);
        Long seriesId = vehicleSource.getSeriesId();//获取车系id，查询指导价，车系名称
        Long ownerId = vehicleSource.getOwnerId();//卖家id，查询卖家手机号、微信号、头像\昵称
        //填入source输入
        vehicleSourceVO.setAge(vehicleSource.getAge());
        vehicleSourceVO.setArea(vehicleSource.getArea());
        vehicleSourceVO.setColor(vehicleSource.getColor());
        vehicleSourceVO.setName(vehicleSource.getName());
        vehicleSourceVO.setNotes(vehicleSource.getNotes());
        vehicleSourceVO.setPrice(vehicleSource.getPrice());
        vehicleSourceVO.setInnerColor(vehicleSource.getInnerColor());
        vehicleSourceVO.setType(vehicleSource.getType());
        vehicleSourceVO.setStatus(vehicleSource.getStatus());
        //查询你车系信息
        VehicleSeries vehicleSeries = vehicleSeriesMapper.selectVehicleSeriesById(seriesId);
        vehicleSourceVO.setSeries(vehicleSeries.getName());
        vehicleSourceVO.setGuidPrice(vehicleSeries.getPrice());

        //查询卖家信息
        VehicleUserinfo vehicleUserinfo = userinfoMapper.selectVehicleUserinfoById(ownerId);
        vehicleSourceVO.setOwnerId(ownerId);
        vehicleSourceVO.setPhone(vehicleUserinfo.getPhone());
        vehicleSourceVO.setAvatar(vehicleUserinfo.getAvatarUrl());
        vehicleSourceVO.setWxNum(vehicleUserinfo.getPhone());
        vehicleSourceVO.setNickName(vehicleUserinfo.getNickName());

        //查询车源相关图片
        List<String> imgList = sourceImgMapper.selectImgListBySourceId(id);
        vehicleSourceVO.setUrls(imgList);
        return R.ok(vehicleSourceVO);
    }

}

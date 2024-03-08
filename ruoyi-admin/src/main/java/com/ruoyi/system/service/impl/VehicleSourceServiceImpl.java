package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.VehicleSeries;
import com.ruoyi.system.domain.VehicleUserinfo;
import com.ruoyi.system.mapper.VehicleSeriesMapper;
import com.ruoyi.system.mapper.VehicleSourceImgMapper;
import com.ruoyi.system.mapper.VehicleUserinfoMapper;
import com.ruoyi.system.vo.VehicleSourceListVO;
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
 * @date 2024-03-06
 */
@Service
public class VehicleSourceServiceImpl implements IVehicleSourceService
{
    @Autowired
    private VehicleSourceMapper vehicleSourceMapper;

    @Autowired
    private VehicleSeriesMapper vehicleSeriesMapper;

    @Autowired
    private VehicleUserinfoMapper vehicleUserinfoMapper;

    @Autowired
    private VehicleSourceImgMapper vehicleSourceImgMapper;

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
    public List<VehicleSourceListVO> getAllVehicleSource() {
        List<VehicleSourceListVO> list = new ArrayList<>();
        VehicleSource vehicleSource = new VehicleSource();

        List<VehicleSource> vehicleSources = vehicleSourceMapper.selectVehicleSourceList(vehicleSource);
        vehicleSources.forEach(e->{
            VehicleSourceListVO vehicleSourceListVO = new VehicleSourceListVO();
            Long seriesId = e.getSeriesId();
            //获取车系，车系指导价 车系图片
            VehicleSeries vehicleSeries = vehicleSeriesMapper.selectVehicleSeriesById(seriesId);
            vehicleSourceListVO.setId(e.getId());
            vehicleSourceListVO.setName(e.getName());
            vehicleSourceListVO.setType(e.getType());
            vehicleSourceListVO.setColor(e.getColor());
            vehicleSourceListVO.setGuidPrice(vehicleSeries.getPrice());
            vehicleSourceListVO.setArea(e.getArea());
            vehicleSourceListVO.setUrl(vehicleSeries.getUrl());
            vehicleSourceListVO.setStatus(e.getStatus());
            //填入list
            list.add(vehicleSourceListVO);
        });
        return list;
    }

    @Override
    public VehicleSourceVO selectVehicleSourceInfoById(Long id) {
        VehicleSourceVO vehicleSourceVO = new VehicleSourceVO();
        //查询车源
        VehicleSource vehicleSource = vehicleSourceMapper.selectVehicleSourceById(id);
        Long seriesId = vehicleSource.getSeriesId();
        Long ownerId = vehicleSource.getOwnerId();

        vehicleSourceVO.setName(vehicleSource.getName());
        vehicleSourceVO.setPrice(vehicleSource.getPrice());
        vehicleSourceVO.setColor(vehicleSource.getColor());
        vehicleSourceVO.setInnerColor(vehicleSource.getInnerColor());
        vehicleSourceVO.setArea(vehicleSource.getArea());
        vehicleSourceVO.setSourceArea(vehicleSource.getSourceArea());
        vehicleSourceVO.setNotes(vehicleSource.getNotes());
        vehicleSourceVO.setStatus(vehicleSource.getStatus());
        vehicleSourceVO.setAge(vehicleSource.getAge());

        //查询车系
        VehicleSeries vehicleSeries = vehicleSeriesMapper.selectVehicleSeriesById(seriesId);
        vehicleSourceVO.setSeries(vehicleSeries.getName());
        vehicleSourceVO.setGuidPrice(vehicleSeries.getPrice());


        //查询图片url
        List<String> url = vehicleSourceImgMapper.selectImgListBySourceId(id);
        vehicleSourceVO.setUrls(url);
        //查询卖家信息
        VehicleUserinfo vehicleUserinfo = vehicleUserinfoMapper.selectVehicleUserinfoById(ownerId);
        vehicleSourceVO.setOwnerId(vehicleUserinfo.getId());
        vehicleSourceVO.setPhone(vehicleUserinfo.getPhone());
        vehicleSourceVO.setWxNum(vehicleUserinfo.getPhone());
        return vehicleSourceVO;
    }
}

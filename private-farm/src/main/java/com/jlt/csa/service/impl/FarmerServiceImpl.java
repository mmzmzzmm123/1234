package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.FarmerMapper;
import com.jlt.csa.domain.Farmer;
import com.jlt.csa.service.IFarmerService;

/**
 * 农场会员Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-04-01
 */
@Service
public class FarmerServiceImpl implements IFarmerService 
{
    @Autowired
    private FarmerMapper farmerMapper;

    /**
     * 查询农场会员
     * 
     * @param farmerId 农场会员主键
     * @return 农场会员
     */
    @Override
    public Farmer selectFarmerByFarmerId(Long farmerId)
    {
        return farmerMapper.selectFarmerByFarmerId(farmerId);
    }

    /**
     * 查询带菜地信息的农场会员
     *
     * @param farmerId 农场会员主键
     * @return 农场会员
     */
    public Farmer selectFarmerWithGardenById(Long farmerId) {
        return farmerMapper.selectFarmerWithGardenById(farmerId);
    }

    /**
     * 查询农场会员列表
     * 
     * @param farmer 农场会员
     * @return 农场会员
     */
    @Override
    public List<Farmer> selectFarmerList(Farmer farmer)
    {
        return farmerMapper.selectFarmerList(farmer);
    }

    /**
     * 新增农场会员
     * 
     * @param farmer 农场会员
     * @return 结果
     */
    @Override
    public int insertFarmer(Farmer farmer)
    {
        farmer.setCreateTime(DateUtils.getNowDate());
        return farmerMapper.insertFarmer(farmer);
    }

    /**
     * 修改农场会员
     * 
     * @param farmer 农场会员
     * @return 结果
     */
    @Override
    public int updateFarmer(Farmer farmer)
    {
        farmer.setUpdateTime(DateUtils.getNowDate());
        return farmerMapper.updateFarmer(farmer);
    }

    /**
     * 批量删除农场会员
     * 
     * @param farmerIds 需要删除的农场会员主键
     * @return 结果
     */
    @Override
    public int deleteFarmerByFarmerIds(Long[] farmerIds)
    {
        return farmerMapper.deleteFarmerByFarmerIds(farmerIds);
    }

    /**
     * 删除农场会员信息
     * 
     * @param farmerId 农场会员主键
     * @return 结果
     */
    @Override
    public int deleteFarmerByFarmerId(Long farmerId)
    {
        return farmerMapper.deleteFarmerByFarmerId(farmerId);
    }
}

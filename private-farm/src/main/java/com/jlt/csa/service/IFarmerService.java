package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.Farmer;

/**
 * 农场会员Service接口
 * 
 * @author JiaLeitao
 * @date 2022-04-01
 */
public interface IFarmerService 
{
    /**
     * 查询农场会员
     * 
     * @param farmerId 农场会员主键
     * @return 农场会员
     */
    public Farmer selectFarmerByFarmerId(Long farmerId);

    /**
     * 查询农场会员列表
     * 
     * @param farmer 农场会员
     * @return 农场会员集合
     */
    public List<Farmer> selectFarmerList(Farmer farmer);

    /**
     * 新增农场会员
     * 
     * @param farmer 农场会员
     * @return 结果
     */
    public int insertFarmer(Farmer farmer);

    /**
     * 修改农场会员
     * 
     * @param farmer 农场会员
     * @return 结果
     */
    public int updateFarmer(Farmer farmer);

    /**
     * 批量删除农场会员
     * 
     * @param farmerIds 需要删除的农场会员主键集合
     * @return 结果
     */
    public int deleteFarmerByFarmerIds(Long[] farmerIds);

    /**
     * 删除农场会员信息
     * 
     * @param farmerId 农场会员主键
     * @return 结果
     */
    public int deleteFarmerByFarmerId(Long farmerId);
}

package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.CsaFarmerMapper;
import com.jlt.csa.domain.CsaFarmer;
import com.jlt.csa.service.ICsaFarmerService;

/**
 * 私家农场会员Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
@Service
public class CsaFarmerServiceImpl implements ICsaFarmerService 
{
    @Autowired
    private CsaFarmerMapper csaFarmerMapper;

    /**
     * 查询私家农场会员
     * 
     * @param id 私家农场会员主键
     * @return 私家农场会员
     */
    @Override
    public CsaFarmer selectCsaFarmerById(Long id)
    {
        return csaFarmerMapper.selectCsaFarmerById(id);
    }

    /**
     * 查询私家农场会员列表
     * 
     * @param csaFarmer 私家农场会员
     * @return 私家农场会员
     */
    @Override
    public List<CsaFarmer> selectCsaFarmerList(CsaFarmer csaFarmer)
    {
        return csaFarmerMapper.selectCsaFarmerList(csaFarmer);
    }

    /**
     * 新增私家农场会员
     * 
     * @param csaFarmer 私家农场会员
     * @return 结果
     */
    @Override
    public int insertCsaFarmer(CsaFarmer csaFarmer)
    {
        csaFarmer.setCreateTime(DateUtils.getNowDate());
        return csaFarmerMapper.insertCsaFarmer(csaFarmer);
    }

    /**
     * 修改私家农场会员
     * 
     * @param csaFarmer 私家农场会员
     * @return 结果
     */
    @Override
    public int updateCsaFarmer(CsaFarmer csaFarmer)
    {
        csaFarmer.setUpdateTime(DateUtils.getNowDate());
        return csaFarmerMapper.updateCsaFarmer(csaFarmer);
    }

    /**
     * 批量删除私家农场会员
     * 
     * @param ids 需要删除的私家农场会员主键
     * @return 结果
     */
    @Override
    public int deleteCsaFarmerByIds(Long[] ids)
    {
        return csaFarmerMapper.deleteCsaFarmerByIds(ids);
    }

    /**
     * 删除私家农场会员信息
     * 
     * @param id 私家农场会员主键
     * @return 结果
     */
    @Override
    public int deleteCsaFarmerById(Long id)
    {
        return csaFarmerMapper.deleteCsaFarmerById(id);
    }
}

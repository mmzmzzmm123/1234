package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.CsaFarmer;

/**
 * 私家农场会员Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
public interface ICsaFarmerService 
{
    /**
     * 查询私家农场会员
     * 
     * @param id 私家农场会员主键
     * @return 私家农场会员
     */
    public CsaFarmer selectCsaFarmerById(Long id);

    /**
     * 查询私家农场会员列表
     * 
     * @param csaFarmer 私家农场会员
     * @return 私家农场会员集合
     */
    public List<CsaFarmer> selectCsaFarmerList(CsaFarmer csaFarmer);

    /**
     * 新增私家农场会员
     * 
     * @param csaFarmer 私家农场会员
     * @return 结果
     */
    public int insertCsaFarmer(CsaFarmer csaFarmer);

    /**
     * 修改私家农场会员
     * 
     * @param csaFarmer 私家农场会员
     * @return 结果
     */
    public int updateCsaFarmer(CsaFarmer csaFarmer);

    /**
     * 批量删除私家农场会员
     * 
     * @param ids 需要删除的私家农场会员主键集合
     * @return 结果
     */
    public int deleteCsaFarmerByIds(Long[] ids);

    /**
     * 删除私家农场会员信息
     * 
     * @param id 私家农场会员主键
     * @return 结果
     */
    public int deleteCsaFarmerById(Long id);
}

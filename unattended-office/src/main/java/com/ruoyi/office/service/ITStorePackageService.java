package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TStorePackage;

/**
 * 店铺充值套餐Service接口
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public interface ITStorePackageService 
{
    /**
     * 查询店铺充值套餐
     * 
     * @param id 店铺充值套餐主键
     * @return 店铺充值套餐
     */
    public TStorePackage selectTStorePackageById(Long id);

    /**
     * 查询店铺充值套餐列表
     * 
     * @param tStorePackage 店铺充值套餐
     * @return 店铺充值套餐集合
     */
    public List<TStorePackage> selectTStorePackageList(TStorePackage tStorePackage);

    /**
     * 新增店铺充值套餐
     * 
     * @param tStorePackage 店铺充值套餐
     * @return 结果
     */
    public int insertTStorePackage(TStorePackage tStorePackage);

    /**
     * 修改店铺充值套餐
     * 
     * @param tStorePackage 店铺充值套餐
     * @return 结果
     */
    public int updateTStorePackage(TStorePackage tStorePackage);

    /**
     * 批量删除店铺充值套餐
     * 
     * @param ids 需要删除的店铺充值套餐主键集合
     * @return 结果
     */
    public int deleteTStorePackageByIds(Long[] ids);

    /**
     * 删除店铺充值套餐信息
     * 
     * @param id 店铺充值套餐主键
     * @return 结果
     */
    public int deleteTStorePackageById(Long id);
}

package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TStorePackageMapper;
import com.ruoyi.office.domain.TStorePackage;
import com.ruoyi.office.service.ITStorePackageService;

/**
 * 店铺充值套餐Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class TStorePackageServiceImpl extends ServiceImpl<TStorePackageMapper, TStorePackage> implements ITStorePackageService
{
    @Autowired
    private TStorePackageMapper tStorePackageMapper;

    /**
     * 查询店铺充值套餐
     * 
     * @param id 店铺充值套餐主键
     * @return 店铺充值套餐
     */
    @Override
    public TStorePackage selectTStorePackageById(Long id)
    {
        return tStorePackageMapper.selectTStorePackageById(id);
    }

    /**
     * 查询店铺充值套餐列表
     * 
     * @param tStorePackage 店铺充值套餐
     * @return 店铺充值套餐
     */
    @Override
    public List<TStorePackage> selectTStorePackageList(TStorePackage tStorePackage)
    {
        return tStorePackageMapper.selectTStorePackageList(tStorePackage);
    }

    /**
     * 新增店铺充值套餐
     * 
     * @param tStorePackage 店铺充值套餐
     * @return 结果
     */
    @Override
    public int insertTStorePackage(TStorePackage tStorePackage)
    {
        tStorePackage.setCreateTime(DateUtils.getNowDate());
        return tStorePackageMapper.insertTStorePackage(tStorePackage);
    }

    /**
     * 修改店铺充值套餐
     * 
     * @param tStorePackage 店铺充值套餐
     * @return 结果
     */
    @Override
    public int updateTStorePackage(TStorePackage tStorePackage)
    {
        tStorePackage.setUpdateTime(DateUtils.getNowDate());
        return tStorePackageMapper.updateTStorePackage(tStorePackage);
    }

    /**
     * 批量删除店铺充值套餐
     * 
     * @param ids 需要删除的店铺充值套餐主键
     * @return 结果
     */
    @Override
    public int deleteTStorePackageByIds(Long[] ids)
    {
        return tStorePackageMapper.deleteTStorePackageByIds(ids);
    }

    /**
     * 删除店铺充值套餐信息
     * 
     * @param id 店铺充值套餐主键
     * @return 结果
     */
    @Override
    public int deleteTStorePackageById(Long id)
    {
        return tStorePackageMapper.deleteTStorePackageById(id);
    }
}

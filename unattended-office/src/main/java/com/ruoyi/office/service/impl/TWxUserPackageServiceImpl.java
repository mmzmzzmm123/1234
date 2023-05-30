package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserPackageMapper;
import com.ruoyi.office.domain.TWxUserPackage;
import com.ruoyi.office.service.ITWxUserPackageService;

/**
 * 用户套餐购买记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class TWxUserPackageServiceImpl extends ServiceImpl<TWxUserPackageMapper, TWxUserPackage> implements ITWxUserPackageService
{
    @Autowired
    private TWxUserPackageMapper tWxUserPackageMapper;

    /**
     * 查询用户套餐购买记录
     *
     * @param id 用户套餐购买记录主键
     * @return 用户套餐购买记录
     */
    @Override
    public TWxUserPackage selectTWxUserPackageById(Long id)
    {
        return tWxUserPackageMapper.selectTWxUserPackageById(id);
    }

    /**
     * 查询用户套餐购买记录列表
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 用户套餐购买记录
     */
    @Override
    public List<TWxUserPackage> selectTWxUserPackageList(TWxUserPackage tWxUserPackage)
    {
        return tWxUserPackageMapper.selectTWxUserPackageList(tWxUserPackage);
    }

    /**
     * 新增用户套餐购买记录
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 结果
     */
    @Override
    public int insertTWxUserPackage(TWxUserPackage tWxUserPackage)
    {
        tWxUserPackage.setCreateTime(DateUtils.getNowDate());
        return tWxUserPackageMapper.insertTWxUserPackage(tWxUserPackage);
    }

    /**
     * 修改用户套餐购买记录
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 结果
     */
    @Override
    public int updateTWxUserPackage(TWxUserPackage tWxUserPackage)
    {
        tWxUserPackage.setUpdateTime(DateUtils.getNowDate());
        return tWxUserPackageMapper.updateTWxUserPackage(tWxUserPackage);
    }

    /**
     * 批量删除用户套餐购买记录
     *
     * @param ids 需要删除的用户套餐购买记录主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserPackageByIds(Long[] ids)
    {
        return tWxUserPackageMapper.deleteTWxUserPackageByIds(ids);
    }

    /**
     * 删除用户套餐购买记录信息
     *
     * @param id 用户套餐购买记录主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserPackageById(Long id)
    {
        return tWxUserPackageMapper.deleteTWxUserPackageById(id);
    }
}

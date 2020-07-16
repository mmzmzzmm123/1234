package com.ruoyi.erp.shop.service.impl;

import java.util.List;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.project.system.domain.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.shop.mapper.ShopTypeMapper;
import com.ruoyi.erp.shop.domain.ShopType;
import com.ruoyi.erp.shop.service.IShopTypeService;

/**
 * 电商平台Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
@Service
public class ShopTypeServiceImpl implements IShopTypeService 
{
    @Autowired
    private ShopTypeMapper shopTypeMapper;

    /**
     * 查询电商平台
     * 
     * @param id 电商平台ID
     * @return 电商平台
     */
    @Override
    public ShopType selectShopTypeById(Long id)
    {
        return shopTypeMapper.selectShopTypeById(id);
    }

    /**
     * 查询电商平台列表
     * 
     * @param shopType 电商平台
     * @return 电商平台
     */
    @Override
    public List<ShopType> selectShopTypeList(ShopType shopType)
    {
        return shopTypeMapper.selectShopTypeList(shopType);
    }

    /**
     * 查询所有平台类型
     *
     * @return 平台列表
     */
    @Override
    public List<ShopType> selectShopTypeAll()
    {
        return shopTypeMapper.selectShopTypeAll();
    }

    /**
     * 新增电商平台
     * 
     * @param shopType 电商平台
     * @return 结果
     */
    @Override
    public int insertShopType(ShopType shopType)
    {
        return shopTypeMapper.insertShopType(shopType);
    }

    /**
     * 修改电商平台
     * 
     * @param shopType 电商平台
     * @return 结果
     */
    @Override
    public int updateShopType(ShopType shopType)
    {
        return shopTypeMapper.updateShopType(shopType);
    }

    /**
     * 批量删除电商平台
     * 
     * @param ids 需要删除的电商平台ID
     * @return 结果
     */
    @Override
    public int deleteShopTypeByIds(Long[] ids)
    {
        return shopTypeMapper.deleteShopTypeByIds(ids);
    }

    /**
     * 删除电商平台信息
     * 
     * @param id 电商平台ID
     * @return 结果
     */
    @Override
    public int deleteShopTypeById(Long id)
    {
        return shopTypeMapper.deleteShopTypeById(id);
    }
}

package com.ruoyi.erp.shop.service;

import java.util.List;
import com.ruoyi.erp.shop.domain.ShopType;
import com.ruoyi.project.system.domain.SysRole;

/**
 * 电商平台Service接口
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
public interface IShopTypeService 
{
    /**
     * 查询电商平台
     * 
     * @param id 电商平台ID
     * @return 电商平台
     */
    public ShopType selectShopTypeById(Long id);

    /**
     * 查询电商平台列表
     * 
     * @param shopType 电商平台
     * @return 电商平台集合
     */
    public List<ShopType> selectShopTypeList(ShopType shopType);

    /**
     * 查询所有平台
     *
     * @return 电商平台列表
     */
    public List<ShopType> selectShopTypeAll();

    /**
     * 新增电商平台
     * 
     * @param shopType 电商平台
     * @return 结果
     */
    public int insertShopType(ShopType shopType);

    /**
     * 修改电商平台
     * 
     * @param shopType 电商平台
     * @return 结果
     */
    public int updateShopType(ShopType shopType);

    /**
     * 批量删除电商平台
     * 
     * @param ids 需要删除的电商平台ID
     * @return 结果
     */
    public int deleteShopTypeByIds(Long[] ids);

    /**
     * 删除电商平台信息
     * 
     * @param id 电商平台ID
     * @return 结果
     */
    public int deleteShopTypeById(Long id);
}

package com.ruoyi.erp.shop.mapper;

import java.util.List;
import com.ruoyi.erp.shop.domain.Shop;

/**
 * 电商店铺Mapper接口
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
public interface ShopMapper 
{
    /**
     * 查询电商店铺
     * 
     * @param id 电商店铺ID
     * @return 电商店铺
     */
    public Shop selectShopById(Long id);

    /**
     * 查询电商店铺列表
     * 
     * @param shop 电商店铺
     * @return 电商店铺集合
     */
    public List<Shop> selectShopList(Shop shop);

    /**
     * 新增电商店铺
     * 
     * @param shop 电商店铺
     * @return 结果
     */
    public int insertShop(Shop shop);

    /**
     * 修改电商店铺
     * 
     * @param shop 电商店铺
     * @return 结果
     */
    public int updateShop(Shop shop);

    /**
     * 删除电商店铺
     * 
     * @param id 电商店铺ID
     * @return 结果
     */
    public int deleteShopById(Long id);

    /**
     * 批量删除电商店铺
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteShopByIds(Long[] ids);
}

package com.ruoyi.erp.shop.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.platform.WishApi;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.erp.shop.mapper.ShopTypeMapper;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.shop.mapper.ShopMapper;
import com.ruoyi.erp.shop.domain.Shop;
import com.ruoyi.erp.shop.service.IShopService;

/**
 * 电商店铺Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
@Service
public class ShopServiceImpl implements IShopService 
{
    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopTypeMapper shopTypeMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询电商店铺
     * 
     * @param id 电商店铺ID
     * @return 电商店铺
     */
    @Override
    public Shop selectShopById(Long id)
    {
        return shopMapper.selectShopById(id);
    }

    /**
     * 查询电商店铺列表
     * 
     * @param shop 电商店铺
     * @return 电商店铺
     */
    @Override
    public List<Shop> selectShopList(Shop shop)
    {
        return shopMapper.selectShopList(shop);
    }

    /**
     * 新增电商店铺
     * 
     * @param shop 电商店铺
     * @return 结果
     */
    @Override
    public int insertShop(Shop shop)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        shop.setAdminId(userId);
        return shopMapper.insertShop(shop);
    }

    /**
     * 修改电商店铺
     * 
     * @param shop 电商店铺
     * @return 结果
     */
    @Override
    public int updateShop(Shop shop)
    {
        if(shop.getShopData() != ""){
            JSONObject obj = JSONObject.parseObject(shop.getShopData());
            String code = obj.getString("code");
            shop.setShopData(WishApi.getAccessToken(shopTypeMapper.selectShopTypeById(shop.getShopTypeId()),code));
        }
        return shopMapper.updateShop(shop);
    }

    /**
     * 批量删除电商店铺
     * 
     * @param ids 需要删除的电商店铺ID
     * @return 结果
     */
    @Override
    public int deleteShopByIds(Long[] ids)
    {
        return shopMapper.deleteShopByIds(ids);
    }

    /**
     * 删除电商店铺信息
     * 
     * @param id 电商店铺ID
     * @return 结果
     */
    @Override
    public int deleteShopById(Long id)
    {
        return shopMapper.deleteShopById(id);
    }
}

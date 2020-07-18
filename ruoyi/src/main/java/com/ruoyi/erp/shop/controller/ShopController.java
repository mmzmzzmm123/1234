package com.ruoyi.erp.shop.controller;

import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.erp.shop.domain.ShopType;
import com.ruoyi.erp.shop.service.IShopTypeService;
import com.ruoyi.project.system.service.ISysPostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.erp.shop.domain.Shop;
import com.ruoyi.erp.shop.service.IShopService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 电商店铺Controller
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
@RestController
@RequestMapping("/shop/shop")
public class ShopController extends BaseController
{
    @Autowired
    private IShopService shopService;

    @Autowired
    private IShopTypeService shopTypeService;

    /**
     * 查询电商店铺列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:list')")
    @GetMapping("/list")
    public TableDataInfo list(Shop shop)
    {
        startPage();
        List<Shop> list = shopService.selectShopList(shop);
        return getDataTable(list);
    }

    /**
     * 导出电商店铺列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:export')")
    @Log(title = "电商店铺", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Shop shop)
    {
        List<Shop> list = shopService.selectShopList(shop);
        ExcelUtil<Shop> util = new ExcelUtil<Shop>(Shop.class);
        return util.exportExcel(list, "shop");
    }

    /**
     * 获取电商店铺详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:query')")
    @GetMapping(value = { "/", "/{id}" })
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id)
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("shopTypes", shopTypeService.selectShopTypeAll());
        if (StringUtils.isNotNull(id))
        {
            ajax.put(AjaxResult.DATA_TAG, shopService.selectShopById(id));
        }
        return ajax;
    }

    /**
     * 新增电商店铺
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:add')")
    @Log(title = "电商店铺", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Shop shop)
    {
        return toAjax(shopService.insertShop(shop));
    }

    /**
     * 修改电商店铺
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:edit')")
    @Log(title = "电商店铺", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Shop shop)
    {
        return toAjax(shopService.updateShop(shop));
    }

    /**
     * 删除电商店铺
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:remove')")
    @Log(title = "电商店铺", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shopService.deleteShopByIds(ids));
    }

    /**
     * 获取平台授权
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:go_auth_url')")
    @GetMapping(value = {"/auth_url/{shopTypeId}" })
    public AjaxResult goAuthUrl(@PathVariable(value = "shopTypeId", required = true) Long shopTypeId){
        AjaxResult ajax = AjaxResult.success();
        ShopType shopType = shopTypeService.selectShopTypeById(shopTypeId);
        ajax.put("go_auth_url", shopType.getAuthUrl());
        return ajax;
    }

    /**
     * 获取平台授权
     */
    @PreAuthorize("@ss.hasPermi('shop:shop:auth_success')")
    @GetMapping(value = {"/auth_success/" })
    public String authSuccess(){
        String html = "";
        return html;
    }

}

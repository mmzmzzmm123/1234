package com.ruoyi.erp.shop.controller;

import java.util.List;
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
import com.ruoyi.erp.shop.domain.ShopType;
import com.ruoyi.erp.shop.service.IShopTypeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 电商平台Controller
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
@RestController
@RequestMapping("/shop/shop_type")
public class ShopTypeController extends BaseController
{
    @Autowired
    private IShopTypeService shopTypeService;

    /**
     * 查询电商平台列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shop_type:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopType shopType)
    {
        startPage();
        List<ShopType> list = shopTypeService.selectShopTypeList(shopType);
        return getDataTable(list);
    }

    /**
     * 导出电商平台列表
     */
    @PreAuthorize("@ss.hasPermi('shop:shop_type:export')")
    @Log(title = "电商平台", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ShopType shopType)
    {
        List<ShopType> list = shopTypeService.selectShopTypeList(shopType);
        ExcelUtil<ShopType> util = new ExcelUtil<ShopType>(ShopType.class);
        return util.exportExcel(list, "shop_type");
    }

    /**
     * 获取电商平台详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:shop_type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(shopTypeService.selectShopTypeById(id));
    }

    /**
     * 新增电商平台
     */
    @PreAuthorize("@ss.hasPermi('shop:shop_type:add')")
    @Log(title = "电商平台", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopType shopType)
    {
        return toAjax(shopTypeService.insertShopType(shopType));
    }

    /**
     * 修改电商平台
     */
    @PreAuthorize("@ss.hasPermi('shop:shop_type:edit')")
    @Log(title = "电商平台", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopType shopType)
    {
        return toAjax(shopTypeService.updateShopType(shopType));
    }

    /**
     * 删除电商平台
     */
    @PreAuthorize("@ss.hasPermi('shop:shop_type:remove')")
    @Log(title = "电商平台", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shopTypeService.deleteShopTypeByIds(ids));
    }
}

package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.office.domain.TStorePromotion;
import com.ruoyi.office.service.ITStorePromotionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 优惠券 t_store_promotionController
 * 
 * @author ruoyi
 * @date 2023-06-14
 */
@RestController
@RequestMapping("/office/storepromotion")
public class TStorePromotionController extends BaseController
{
    @Autowired
    private ITStorePromotionService tStorePromotionService;

    /**
     * 查询优惠券 t_store_promotion列表
     */
    @PreAuthorize("@ss.hasPermi('office:storepromotion:list')")
    @GetMapping("/list")
    public TableDataInfo list(TStorePromotion tStorePromotion)
    {
        tStorePromotion.setMerchantId(SecurityUtils.getUserId());
        startPage();
        List<TStorePromotion> list = tStorePromotionService.selectTStorePromotionList(tStorePromotion);
        return getDataTable(list);
    }

    /**
     * 导出优惠券 t_store_promotion列表
     */
    @PreAuthorize("@ss.hasPermi('office:storepromotion:export')")
    @Log(title = "优惠券 t_store_promotion", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TStorePromotion tStorePromotion)
    {
        List<TStorePromotion> list = tStorePromotionService.selectTStorePromotionList(tStorePromotion);
        ExcelUtil<TStorePromotion> util = new ExcelUtil<TStorePromotion>(TStorePromotion.class);
        util.exportExcel(response, list, "优惠券 t_store_promotion数据");
    }

    /**
     * 获取优惠券 t_store_promotion详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:storepromotion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tStorePromotionService.selectTStorePromotionById(id));
    }

    /**
     * 新增优惠券 t_store_promotion
     */
    @PreAuthorize("@ss.hasPermi('office:storepromotion:add')")
    @Log(title = "优惠券 t_store_promotion", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TStorePromotion tStorePromotion)
    {
        return toAjax(tStorePromotionService.insertTStorePromotion(tStorePromotion));
    }

    /**
     * 修改优惠券 t_store_promotion
     */
    @PreAuthorize("@ss.hasPermi('office:storepromotion:edit')")
    @Log(title = "优惠券 t_store_promotion", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TStorePromotion tStorePromotion)
    {
        return toAjax(tStorePromotionService.updateTStorePromotion(tStorePromotion));
    }

    /**
     * 删除优惠券 t_store_promotion
     */
    @PreAuthorize("@ss.hasPermi('office:storepromotion:remove')")
    @Log(title = "优惠券 t_store_promotion", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tStorePromotionService.deleteTStorePromotionByIds(ids));
    }
}

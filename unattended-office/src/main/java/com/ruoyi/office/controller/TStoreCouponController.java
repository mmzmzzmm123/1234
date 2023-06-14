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
import com.ruoyi.office.domain.TStoreCoupon;
import com.ruoyi.office.service.ITStoreCouponService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 卡券Controller
 *
 * @author ruoyi
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/office/storecoupon")
public class TStoreCouponController extends BaseController {
    @Autowired
    private ITStoreCouponService tStoreCouponService;

    /**
     * 查询卡券列表
     */
    @PreAuthorize("@ss.hasPermi('office:storecoupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(TStoreCoupon tStoreCoupon) {
        tStoreCoupon.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TStoreCoupon> list = tStoreCouponService.selectTStoreCouponList(tStoreCoupon);
        return getDataTable(list);
    }

    /**
     * 导出卡券列表
     */
    @PreAuthorize("@ss.hasPermi('office:storecoupon:export')")
    @Log(title = "卡券", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TStoreCoupon tStoreCoupon) {
        List<TStoreCoupon> list = tStoreCouponService.selectTStoreCouponList(tStoreCoupon);
        ExcelUtil<TStoreCoupon> util = new ExcelUtil<TStoreCoupon>(TStoreCoupon.class);
        util.exportExcel(response, list, "卡券数据");
    }

    /**
     * 获取卡券详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:storecoupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tStoreCouponService.selectTStoreCouponById(id));
    }

    /**
     * 新增卡券
     */
    @PreAuthorize("@ss.hasPermi('office:storecoupon:add')")
    @Log(title = "卡券", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TStoreCoupon tStoreCoupon) {
        tStoreCoupon.setCreateBy(SecurityUtils.getUserId() + "");
        return toAjax(tStoreCouponService.insertTStoreCoupon(tStoreCoupon));
    }

    /**
     * 修改卡券
     */
    @PreAuthorize("@ss.hasPermi('office:storecoupon:edit')")
    @Log(title = "卡券", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TStoreCoupon tStoreCoupon) {
        tStoreCoupon.setUpdateBy(SecurityUtils.getUserId() + "");
        return toAjax(tStoreCouponService.updateTStoreCoupon(tStoreCoupon));
    }

    /**
     * 删除卡券
     */
    @PreAuthorize("@ss.hasPermi('office:storecoupon:remove')")
    @Log(title = "卡券", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tStoreCouponService.deleteTStoreCouponByIds(ids));
    }
}

package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.BuyCouponReq;
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
import com.ruoyi.office.domain.TWxUserCoupon;
import com.ruoyi.office.service.ITWxUserCouponService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户优惠券Controller
 *
 * @author ruoyi
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/office/wxusercoupon")
public class TWxUserCouponController extends BaseController {
    @Autowired
    private ITWxUserCouponService tWxUserCouponService;

    /**
     * 查询用户优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxusercoupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWxUserCoupon tWxUserCoupon) {
        startPage();
        List<TWxUserCoupon> list = tWxUserCouponService.selectTWxUserCouponList(tWxUserCoupon);
        return getDataTable(list);
    }

    /**
     * 导出用户优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxusercoupon:export')")
    @Log(title = "用户优惠券", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxUserCoupon tWxUserCoupon) {
        List<TWxUserCoupon> list = tWxUserCouponService.selectTWxUserCouponList(tWxUserCoupon);
        ExcelUtil<TWxUserCoupon> util = new ExcelUtil<TWxUserCoupon>(TWxUserCoupon.class);
        util.exportExcel(response, list, "用户优惠券数据");
    }

    /**
     * 获取用户优惠券详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:wxusercoupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tWxUserCouponService.selectTWxUserCouponById(id));
    }

    /**
     * 新增用户优惠券
     */
    @PreAuthorize("@ss.hasPermi('office:wxusercoupon:add')")
    @Log(title = "用户优惠券", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxUserCoupon tWxUserCoupon) {
        return toAjax(tWxUserCouponService.insertTWxUserCoupon(tWxUserCoupon));
    }

    /**
     * 购买优惠券
     */
    @PreAuthorize("@ss.hasPermi('office:wxusercoupon:add')")
    @Log(title = "用户优惠券", businessType = BusinessType.INSERT)
    @PostMapping("/buy")
    public AjaxResult buy(@RequestBody BuyCouponReq tWxUserCoupon) {
        tWxUserCoupon.setUserId(SecurityUtils.getUserId());

        return AjaxResult.success(tWxUserCouponService.buy(tWxUserCoupon));
    }

    /**
     * 修改用户优惠券
     */
    @PreAuthorize("@ss.hasPermi('office:wxusercoupon:edit')")
    @Log(title = "用户优惠券", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxUserCoupon tWxUserCoupon) {
        return toAjax(tWxUserCouponService.updateTWxUserCoupon(tWxUserCoupon));
    }

    /**
     * 删除用户优惠券
     */
    @PreAuthorize("@ss.hasPermi('office:wxusercoupon:remove')")
    @Log(title = "用户优惠券", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tWxUserCouponService.deleteTWxUserCouponByIds(ids));
    }
}

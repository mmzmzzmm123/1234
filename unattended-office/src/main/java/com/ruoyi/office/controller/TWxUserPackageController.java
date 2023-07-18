package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.BuyStorePackReq;
import com.ruoyi.office.domain.vo.PrepayResp;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.office.domain.TWxUserPackage;
import com.ruoyi.office.service.ITWxUserPackageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户套餐购买记录Controller
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/office/wxuserpackage")
public class TWxUserPackageController extends BaseController {
    @Autowired
    private ITWxUserPackageService tWxUserPackageService;

    /**
     * 查询用户套餐购买记录列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpackage:list')")
    @ApiOperation("商户查询套餐购买记录")
    @GetMapping("/list")
    public TableDataInfo list(TWxUserPackage tWxUserPackage) {
        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
            tWxUserPackage.setMerchant(SecurityUtils.getUserId());
        startPage();
        List<TWxUserPackage> list = tWxUserPackageService.selectTWxUserPackageList(tWxUserPackage);
        return getDataTable(list);
    }

    /**
     * 导出用户套餐购买记录列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpackage:export')")
    @Log(title = "用户套餐购买记录" , businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxUserPackage tWxUserPackage) {
        List<TWxUserPackage> list = tWxUserPackageService.selectTWxUserPackageList(tWxUserPackage);
        ExcelUtil<TWxUserPackage> util = new ExcelUtil<TWxUserPackage>(TWxUserPackage.class);
        util.exportExcel(response, list, "用户套餐购买记录数据");
    }

    /**
     * 获取用户套餐购买记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpackage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tWxUserPackageService.selectTWxUserPackageById(id));
    }

    /**
     * 新增用户套餐购买记录
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpackage:add')")
    @Log(title = "用户套餐购买记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxUserPackage tWxUserPackage) {
        tWxUserPackage.setMerchant(SecurityUtils.getUserId());
        return toAjax(tWxUserPackageService.insertTWxUserPackage(tWxUserPackage));
    }

    /**
     * 修改用户套餐购买记录
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpackage:edit')")
    @Log(title = "用户套餐购买记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxUserPackage tWxUserPackage) {
        return toAjax(tWxUserPackageService.updateTWxUserPackage(tWxUserPackage));
    }

    /**
     * 删除用户套餐购买记录
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpackage:remove')")
    @Log(title = "用户套餐购买记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tWxUserPackageService.deleteTWxUserPackageByIds(ids));
    }



}

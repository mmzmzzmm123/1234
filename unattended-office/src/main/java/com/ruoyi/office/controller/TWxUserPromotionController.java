package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.office.domain.TWxUserPromotion;
import com.ruoyi.office.service.ITWxUserPromotionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户优惠券 t_wx_user_promotionController
 *
 * @author ruoyi
 * @date 2023-06-14
 */
@RestController
@RequestMapping("/office/wxuserpromotion")
public class TWxUserPromotionController extends BaseController {
    @Autowired
    private ITWxUserPromotionService tWxUserPromotionService;

    /**
     * 查询用户优惠券 t_wx_user_promotion列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpromotion:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWxUserPromotion tWxUserPromotion) {
        startPage();
        List<TWxUserPromotion> list = tWxUserPromotionService.selectTWxUserPromotionList(tWxUserPromotion);
        return getDataTable(list);
    }

    /**
     * 导出用户优惠券 t_wx_user_promotion列表
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpromotion:export')")
    @Log(title = "用户优惠券 t_wx_user_promotion", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxUserPromotion tWxUserPromotion) {
        List<TWxUserPromotion> list = tWxUserPromotionService.selectTWxUserPromotionList(tWxUserPromotion);
        ExcelUtil<TWxUserPromotion> util = new ExcelUtil<TWxUserPromotion>(TWxUserPromotion.class);
        util.exportExcel(response, list, "用户优惠券 t_wx_user_promotion数据");
    }

    /**
     * 获取用户优惠券 t_wx_user_promotion详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpromotion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tWxUserPromotionService.selectTWxUserPromotionById(id));
    }

    /**
     * 新增用户优惠券 t_wx_user_promotion
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpromotion:add')")
    @Log(title = "用户优惠券 t_wx_user_promotion", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxUserPromotion tWxUserPromotion) {
        return toAjax(tWxUserPromotionService.insertTWxUserPromotion(tWxUserPromotion));
    }

    /**
     * 修改用户优惠券 t_wx_user_promotion
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpromotion:edit')")
    @Log(title = "用户优惠券 t_wx_user_promotion", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxUserPromotion tWxUserPromotion) {
        return toAjax(tWxUserPromotionService.updateTWxUserPromotion(tWxUserPromotion));
    }

    /**
     * 删除用户优惠券 t_wx_user_promotion
     */
    @PreAuthorize("@ss.hasPermi('office:wxuserpromotion:remove')")
    @Log(title = "用户优惠券 t_wx_user_promotion", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tWxUserPromotionService.deleteTWxUserPromotionByIds(ids));
    }


}

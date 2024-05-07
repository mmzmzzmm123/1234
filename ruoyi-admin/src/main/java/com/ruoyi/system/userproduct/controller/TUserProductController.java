package com.ruoyi.system.userproduct.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.userproduct.domain.TUserProduct;
import com.ruoyi.system.userproduct.service.ITUserProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户订阅产品Controller
 * 
 * @author ruoyi
 * @date 2024-04-23
 */
@RestController
@RequestMapping("/system/userproduct")
public class TUserProductController extends BaseController
{
    @Autowired
    private ITUserProductService tUserProductService;

    /**
     * 查询用户订阅产品列表
     */
    @PreAuthorize("@ss.hasPermi('system:userproduct:list')")
    @GetMapping("/list")
    public TableDataInfo list(TUserProduct tUserProduct)
    {
        startPage();
        List<TUserProduct> list = tUserProductService.selectTUserProductList(tUserProduct);
        return getDataTable(list);
    }

    /**
     * 导出用户订阅产品列表
     */
    @PreAuthorize("@ss.hasPermi('system:userproduct:export')")
    @Log(title = "用户订阅产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TUserProduct tUserProduct)
    {
        List<TUserProduct> list = tUserProductService.selectTUserProductList(tUserProduct);
        ExcelUtil<TUserProduct> util = new ExcelUtil<TUserProduct>(TUserProduct.class);
        util.exportExcel(response, list, "用户订阅产品数据");
    }

    /**
     * 获取用户订阅产品详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:userproduct:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tUserProductService.selectTUserProductById(id));
    }

    /**
     * 新增用户订阅产品
     */
    @PreAuthorize("@ss.hasPermi('system:userproduct:add')")
    @Log(title = "用户订阅产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TUserProduct tUserProduct)
    {
        return toAjax(tUserProductService.insertTUserProduct(tUserProduct));
    }

    /**
     * 修改用户订阅产品
     */
    @PreAuthorize("@ss.hasPermi('system:userproduct:edit')")
    @Log(title = "用户订阅产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TUserProduct tUserProduct)
    {
        return toAjax(tUserProductService.updateTUserProduct(tUserProduct));
    }

    /**
     * 删除用户订阅产品
     */
    @PreAuthorize("@ss.hasPermi('system:userproduct:remove')")
    @Log(title = "用户订阅产品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tUserProductService.deleteTUserProductByIds(ids));
    }
}

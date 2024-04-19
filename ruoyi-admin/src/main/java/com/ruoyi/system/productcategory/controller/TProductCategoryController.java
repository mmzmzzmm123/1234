package com.ruoyi.system.productcategory.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
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
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.service.ITProductCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品和分类关联Controller
 *
 * @author ruoyi
 * @date 2024-04-19
 */
@RestController
@RequestMapping("/system/productcategory")
public class TProductCategoryController extends BaseController
{
    @Autowired
    private ITProductCategoryService tProductCategoryService;

    /**
     * 查询商品和分类关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:productcategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(TProductCategory tProductCategory)
    {
        startPage();
        List<TProductCategory> list = tProductCategoryService.selectTProductCategoryList(tProductCategory);
        return getDataTable(list);
    }


    /**
     * 导出商品和分类关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:productcategory:export')")
    @Log(title = "商品和分类关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TProductCategory tProductCategory)
    {
        List<TProductCategory> list = tProductCategoryService.selectTProductCategoryList(tProductCategory);
        ExcelUtil<TProductCategory> util = new ExcelUtil<TProductCategory>(TProductCategory.class);
        util.exportExcel(response, list, "商品和分类关联数据");
    }

    /**
     * 获取商品和分类关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:productcategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tProductCategoryService.selectTProductCategoryById(id));
    }

    /**
     * 新增商品和分类关联
     */
    @PreAuthorize("@ss.hasPermi('system:productcategory:add')")
    @Log(title = "商品和分类关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProductCategory tProductCategory)
    {
        return toAjax(tProductCategoryService.insertTProductCategory(tProductCategory));
    }

    /**
     * 修改商品和分类关联
     */
    @PreAuthorize("@ss.hasPermi('system:productcategory:edit')")
    @Log(title = "商品和分类关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProductCategory tProductCategory)
    {
        return toAjax(tProductCategoryService.updateTProductCategory(tProductCategory));
    }

    /**
     * 删除商品和分类关联
     */
    @PreAuthorize("@ss.hasPermi('system:productcategory:remove')")
    @Log(title = "商品和分类关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tProductCategoryService.deleteTProductCategoryByIds(ids));
    }
}

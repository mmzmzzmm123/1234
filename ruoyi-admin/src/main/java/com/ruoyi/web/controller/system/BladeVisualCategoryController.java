package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.page.VisualRespData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BladeVisualCategory;
import com.ruoyi.system.service.IBladeVisualCategoryService;

/**
 * 可视化分类Controller
 *
 * @author yangsj
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/avue/api/category")
public class BladeVisualCategoryController extends BaseController
{
    @Autowired
    private IBladeVisualCategoryService bladeVisualCategoryService;

    /**
     * 查询可视化分类列表
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_category:list')")
    @GetMapping("/list")
    public VisualRespData list(BladeVisualCategory bladeVisualCategory)
    {
        startPage();
        List<BladeVisualCategory> list = bladeVisualCategoryService.selectBladeVisualCategoryList(bladeVisualCategory);
        return getVisualData(list);
    }

//    /**
//     * 导出可视化分类列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:visual_category:export')")
//    @Log(title = "可视化分类", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(BladeVisualCategory bladeVisualCategory)
//    {
//        List<BladeVisualCategory> list = bladeVisualCategoryService.selectBladeVisualCategoryList(bladeVisualCategory);
//        ExcelUtil<BladeVisualCategory> util = new ExcelUtil<BladeVisualCategory>(BladeVisualCategory.class);
//        return util.exportExcel(list, "可视化分类数据");
//    }

    /**
     * 获取可视化分类详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_category:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(Long id)
    {
        return AjaxResult.success(bladeVisualCategoryService.selectBladeVisualCategoryById(id));
    }

    /**
     * 新增可视化分类
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_category:add')")
    @Log(title = "可视化分类", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody BladeVisualCategory bladeVisualCategory)
    {
        return toAjax(bladeVisualCategoryService.insertBladeVisualCategory(bladeVisualCategory));
    }

    /**
     * 修改可视化分类
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_category:edit')")
    @Log(title = "可视化分类", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody BladeVisualCategory bladeVisualCategory)
    {
        return toAjax(bladeVisualCategoryService.updateBladeVisualCategory(bladeVisualCategory));
    }

    /**
     * 删除可视化分类
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_category:remove')")
    @Log(title = "可视化分类", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
    public AjaxResult remove(Long[] ids)
    {
        return toAjax(bladeVisualCategoryService.deleteBladeVisualCategoryByIds(ids));
    }
}

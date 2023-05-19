package com.ruoyi.stu.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stu.domain.StuMaterial;
import com.ruoyi.stu.service.IStuMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 提交材料参数Controller
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/stu/material")
public class StuMaterialController extends BaseController
{
    @Autowired
    private IStuMaterialService stuMaterialService;

    /**
     * 查询提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuMaterial stuMaterial)
    {
        startPage();
        List<StuMaterial> list = stuMaterialService.selectStuMaterialList(stuMaterial);
        return getDataTable(list);
    }

    /**
     * 导出提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:material:export')")
    @Log(title = "提交材料参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StuMaterial stuMaterial)
    {
        List<StuMaterial> list = stuMaterialService.selectStuMaterialList(stuMaterial);
        ExcelUtil<StuMaterial> util = new ExcelUtil<StuMaterial>(StuMaterial.class);
        util.exportExcel(response, list, "提交材料参数数据");
    }

    /**
     * 获取提交材料参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('stu:material:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stuMaterialService.selectStuMaterialById(id));
    }

    /**
     * 新增提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:material:add')")
    @Log(title = "提交材料参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuMaterial stuMaterial)
    {
        return toAjax(stuMaterialService.insertStuMaterial(stuMaterial));
    }

    /**
     * 修改提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:material:edit')")
    @Log(title = "提交材料参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StuMaterial stuMaterial)
    {
        return toAjax(stuMaterialService.updateStuMaterial(stuMaterial));
    }

    /**
     * 删除提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:material:remove')")
    @Log(title = "提交材料参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuMaterialService.deleteStuMaterialByIds(ids));
    }
}

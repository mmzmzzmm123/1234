package com.ruoyi.stu.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.StuInfoMaterial;
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
@RequestMapping("/stu/biye")
public class BiyeController extends BaseController
{
    @Autowired
    private IStuInfoMaterialService stuInfoMaterialService;

    /**
     * 查询提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuInfoMaterial stuInfoMaterial)
    {
        startPage();
        List<StuInfoMaterial> list = stuInfoMaterialService.selectStuMaterialList(stuInfoMaterial);
        return getDataTable(list);
    }

    /**
     * 导出提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:export')")
    @Log(title = "提交材料参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StuInfoMaterial stuInfoMaterial)
    {
        List<StuInfoMaterial> list = stuInfoMaterialService.selectStuMaterialList(stuInfoMaterial);
        ExcelUtil<StuInfoMaterial> util = new ExcelUtil<StuInfoMaterial>(StuInfoMaterial.class);
        util.exportExcel(response, list, "提交材料参数数据");
    }



    /**
     * 新增提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:add')")
    @Log(title = "提交材料参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuInfoMaterial stuInfoMaterial)
    {
        return toAjax(stuInfoMaterialService.insertStuMaterial(stuInfoMaterial));
    }

    /**
     * 修改提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:edit')")
    @Log(title = "提交材料参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StuInfoMaterial stuInfoMaterial)
    {
        return toAjax(stuInfoMaterialService.updateStuMaterial(stuInfoMaterial));
    }

    /**
     * 删除提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:remove')")
    @Log(title = "提交材料参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuInfoMaterialService.deleteStuInfoMaterialByIds(ids));
    }
}

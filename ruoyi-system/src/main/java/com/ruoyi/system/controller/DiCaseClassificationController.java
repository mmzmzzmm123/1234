package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.DiCaseClassification;
import com.ruoyi.system.service.IDiCaseClassificationService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 案例分类Controller
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@RestController
@RequestMapping("/system/classification")
public class DiCaseClassificationController extends BaseController
{
    @Autowired
    private IDiCaseClassificationService diCaseClassificationService;

    /**
     * 查询案例分类列表
     */
    @PreAuthorize("@ss.hasPermi('system:classification:list')")
    @GetMapping("/list")
    public AjaxResult list(DiCaseClassification diCaseClassification)
    {
        List<DiCaseClassification> list = diCaseClassificationService.selectDiCaseClassificationList(diCaseClassification);
        return success(list);
    }

    /**
     * 导出案例分类列表
     */
    @PreAuthorize("@ss.hasPermi('system:classification:export')")
    @Log(title = "案例分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DiCaseClassification diCaseClassification)
    {
        List<DiCaseClassification> list = diCaseClassificationService.selectDiCaseClassificationList(diCaseClassification);
        ExcelUtil<DiCaseClassification> util = new ExcelUtil<DiCaseClassification>(DiCaseClassification.class);
        util.exportExcel(response, list, "案例分类数据");
    }

    /**
     * 获取案例分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:classification:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(diCaseClassificationService.selectDiCaseClassificationById(id));
    }

    /**
     * 新增案例分类
     */
    @PreAuthorize("@ss.hasPermi('system:classification:add')")
    @Log(title = "案例分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DiCaseClassification diCaseClassification)
    {
        return toAjax(diCaseClassificationService.insertDiCaseClassification(diCaseClassification));
    }

    /**
     * 修改案例分类
     */
    @PreAuthorize("@ss.hasPermi('system:classification:edit')")
    @Log(title = "案例分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DiCaseClassification diCaseClassification)
    {
        return toAjax(diCaseClassificationService.updateDiCaseClassification(diCaseClassification));
    }

    /**
     * 删除案例分类
     */
    @PreAuthorize("@ss.hasPermi('system:classification:remove')")
    @Log(title = "案例分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(diCaseClassificationService.deleteDiCaseClassificationByIds(ids));
    }
}

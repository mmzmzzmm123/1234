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
import com.ruoyi.system.domain.DiClassicCase;
import com.ruoyi.system.service.IDiClassicCaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 典型案例Controller
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@RestController
@RequestMapping("/system/case")
public class DiClassicCaseController extends BaseController
{
    @Autowired
    private IDiClassicCaseService diClassicCaseService;

    /**
     * 查询典型案例列表
     */
    @PreAuthorize("@ss.hasPermi('system:case:list')")
    @GetMapping("/list")
    public TableDataInfo list(DiClassicCase diClassicCase)
    {
        startPage();
        List<DiClassicCase> list = diClassicCaseService.selectDiClassicCaseList(diClassicCase);
        return getDataTable(list);
    }

    /**
     * 导出典型案例列表
     */
    @PreAuthorize("@ss.hasPermi('system:case:export')")
    @Log(title = "典型案例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DiClassicCase diClassicCase)
    {
        List<DiClassicCase> list = diClassicCaseService.selectDiClassicCaseList(diClassicCase);
        ExcelUtil<DiClassicCase> util = new ExcelUtil<DiClassicCase>(DiClassicCase.class);
        util.exportExcel(response, list, "典型案例数据");
    }

    /**
     * 获取典型案例详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:case:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(diClassicCaseService.selectDiClassicCaseById(id));
    }

    /**
     * 新增典型案例
     */
    @PreAuthorize("@ss.hasPermi('system:case:add')")
    @Log(title = "典型案例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DiClassicCase diClassicCase)
    {
        return toAjax(diClassicCaseService.insertDiClassicCase(diClassicCase));
    }

    /**
     * 修改典型案例
     */
    @PreAuthorize("@ss.hasPermi('system:case:edit')")
    @Log(title = "典型案例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DiClassicCase diClassicCase)
    {
        return toAjax(diClassicCaseService.updateDiClassicCase(diClassicCase));
    }

    /**
     * 删除典型案例
     */
    @PreAuthorize("@ss.hasPermi('system:case:remove')")
    @Log(title = "典型案例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(diClassicCaseService.deleteDiClassicCaseByIds(ids));
    }
}

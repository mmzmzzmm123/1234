package com.gox.basic.controller;

import java.util.List;
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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.basic.domain.Work;
import com.gox.basic.service.IWorkService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 *   Controller
 * 
 * @author gox
 * @date 2021-03-11
 */
@RestController
@RequestMapping("/basic/work")
public class WorkController extends BaseController{
    @Autowired
    private IWorkService workService;

    /**
     * 查询  列表
     */
    @PreAuthorize("@ss.hasPermi('basic:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(Work work)
    {
        startPage();
        List<Work> list = workService.selectWorkList(work);
        return getDataTable(list);
    }

    /**
     * 导出  列表
     */
    @PreAuthorize("@ss.hasPermi('basic:work:export')")
    @Log(title = "  ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Work work)
    {
        List<Work> list = workService.selectWorkList(work);
        ExcelUtil<Work> util = new ExcelUtil<Work>(Work.class);
        return util.exportExcel(list, "work");
    }

    /**
     * 获取  详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:work:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(workService.selectWorkById(id));
    }

    /**
     * 新增  
     */
    @PreAuthorize("@ss.hasPermi('basic:work:add')")
    @Log(title = "  ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Work work)
    {
        return toAjax(workService.insertWork(work));
    }

    /**
     * 修改  
     */
    @PreAuthorize("@ss.hasPermi('basic:work:edit')")
    @Log(title = "  ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Work work)
    {
        return toAjax(workService.updateWork(work));
    }

    /**
     * 删除  
     */
    @PreAuthorize("@ss.hasPermi('basic:work:remove')")
    @Log(title = "  ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(workService.deleteWorkByIds(ids));
    }
}

package com.ruoyi.udef.controller;

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
import com.ruoyi.udef.domain.DfEscapeSchedule;
import com.ruoyi.udef.service.IDfEscapeScheduleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划Controller
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/udef/schedule")
public class DfEscapeScheduleController extends BaseController
{
    @Autowired
    private IDfEscapeScheduleService dfEscapeScheduleService;

    /**
     * 查询计划列表
     */
    @PreAuthorize("@ss.hasPermi('udef:schedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(DfEscapeSchedule dfEscapeSchedule)
    {
        startPage();
        List<DfEscapeSchedule> list = dfEscapeScheduleService.selectDfEscapeScheduleList(dfEscapeSchedule);
        return getDataTable(list);
    }

    /**
     * 导出计划列表
     */
    @PreAuthorize("@ss.hasPermi('udef:schedule:export')")
    @Log(title = "计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DfEscapeSchedule dfEscapeSchedule)
    {
        List<DfEscapeSchedule> list = dfEscapeScheduleService.selectDfEscapeScheduleList(dfEscapeSchedule);
        ExcelUtil<DfEscapeSchedule> util = new ExcelUtil<DfEscapeSchedule>(DfEscapeSchedule.class);
        util.exportExcel(response, list, "计划数据");
    }

    /**
     * 获取计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('udef:schedule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dfEscapeScheduleService.selectDfEscapeScheduleById(id));
    }

    /**
     * 新增计划
     */
    @PreAuthorize("@ss.hasPermi('udef:schedule:add')")
    @Log(title = "计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DfEscapeSchedule dfEscapeSchedule)
    {
        return toAjax(dfEscapeScheduleService.insertDfEscapeSchedule(dfEscapeSchedule));
    }

    /**
     * 修改计划
     */
    @PreAuthorize("@ss.hasPermi('udef:schedule:edit')")
    @Log(title = "计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DfEscapeSchedule dfEscapeSchedule)
    {
        return toAjax(dfEscapeScheduleService.updateDfEscapeSchedule(dfEscapeSchedule));
    }

    /**
     * 删除计划
     */
    @PreAuthorize("@ss.hasPermi('udef:schedule:remove')")
    @Log(title = "计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dfEscapeScheduleService.deleteDfEscapeScheduleByIds(ids));
    }
}

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
import com.ruoyi.udef.domain.DfRobotAlarm;
import com.ruoyi.udef.service.IDfRobotAlarmService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报警Controller
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
@RestController
@RequestMapping("/udef/alarm")
public class DfRobotAlarmController extends BaseController
{
    @Autowired
    private IDfRobotAlarmService dfRobotAlarmService;

    /**
     * 查询报警列表
     */
    @PreAuthorize("@ss.hasPermi('udef:alarm:list')")
    @GetMapping("/list")
    public TableDataInfo list(DfRobotAlarm dfRobotAlarm)
    {
        startPage();
        List<DfRobotAlarm> list = dfRobotAlarmService.selectDfRobotAlarmList(dfRobotAlarm);
        return getDataTable(list);
    }

    /**
     * 导出报警列表
     */
    @PreAuthorize("@ss.hasPermi('udef:alarm:export')")
    @Log(title = "报警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DfRobotAlarm dfRobotAlarm)
    {
        List<DfRobotAlarm> list = dfRobotAlarmService.selectDfRobotAlarmList(dfRobotAlarm);
        ExcelUtil<DfRobotAlarm> util = new ExcelUtil<DfRobotAlarm>(DfRobotAlarm.class);
        util.exportExcel(response, list, "报警数据");
    }

    /**
     * 获取报警详细信息
     */
    @PreAuthorize("@ss.hasPermi('udef:alarm:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dfRobotAlarmService.selectDfRobotAlarmById(id));
    }

    /**
     * 新增报警
     */
    @PreAuthorize("@ss.hasPermi('udef:alarm:add')")
    @Log(title = "报警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DfRobotAlarm dfRobotAlarm)
    {
        return toAjax(dfRobotAlarmService.insertDfRobotAlarm(dfRobotAlarm));
    }

    /**
     * 修改报警
     */
    @PreAuthorize("@ss.hasPermi('udef:alarm:edit')")
    @Log(title = "报警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DfRobotAlarm dfRobotAlarm)
    {
        return toAjax(dfRobotAlarmService.updateDfRobotAlarm(dfRobotAlarm));
    }

    /**
     * 删除报警
     */
    @PreAuthorize("@ss.hasPermi('udef:alarm:remove')")
    @Log(title = "报警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dfRobotAlarmService.deleteDfRobotAlarmByIds(ids));
    }
}

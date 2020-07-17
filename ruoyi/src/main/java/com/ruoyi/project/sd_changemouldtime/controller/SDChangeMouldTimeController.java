package com.ruoyi.project.sd_changemouldtime.controller;

import com.ruoyi.db.sd_mssql.DB_Ajax_DashBoard_ChangeMouldTime_8053;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/sd_changemouldtime")
public class SDChangeMouldTimeController extends BaseController
{
//    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping("/list")
    public AjaxResult DoGetChangeMouldTimeList() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_ChangeMouldTime_8053.DoGetChangeMouldTimeList());
    }


    @GetMapping("/DoGetChangeMouldTimeListDateRange/{fDateStart}/{fDateEnd}")
    public AjaxResult DoGetChangeMouldTimeListDateRange(@PathVariable String fDateStart, @PathVariable String fDateEnd) throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_ChangeMouldTime_8053.DoGetChangeMouldTimeListDateRange(fDateStart,fDateEnd));
    }


    @GetMapping("/rangetimeavg/{fDateStart}/{fDateEnd}")
    public AjaxResult getRangeTimeAvg(@PathVariable String fDateStart, @PathVariable String fDateEnd) throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_ChangeMouldTime_8053.DoGet_avg_changemouldtime_date_range(fDateStart,fDateEnd));
    }



}

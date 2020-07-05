package com.ruoyi.project.fx_changemouldtime.controller;

import com.ruoyi.db.mssql.DB_Ajax_DashBoard_ChangeMouldTime_48102;
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
@RequestMapping("/fx_changemouldtime")
public class ChangeMouldTimeController extends BaseController
{
//    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping("/list")
    public AjaxResult get30Day() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_ChangeMouldTime_48102.DoGetChangeMouldTimeList());
    }

    @GetMapping("/rangetimeavg/{fDateStart}/{fDateEnd}")
    public AjaxResult getRangeTimeAvg(@PathVariable String fDateStart, @PathVariable String fDateEnd) throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_ChangeMouldTime_48102.DoGet_avg_changemouldtime_date_range(fDateStart,fDateEnd));
    }



}

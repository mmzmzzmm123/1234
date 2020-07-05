package com.ruoyi.project.fx_dashboard.controller;

import com.ruoyi.db.mssql.DB_Ajax_DashBoard_48102;
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
@RequestMapping("/fx_dashboard")
public class DashBoardController extends BaseController
{
//    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping("/30day")
    public AjaxResult get30Day() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_48102.DoGet30Day());
    }


    @GetMapping("/lastday")
    public AjaxResult getLastDay() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_48102.DoGetLastDay());
    }


    @GetMapping("/group")
    public AjaxResult getGroup() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_48102.DoGetCurrentTimeGroup());
    }


    @GetMapping("/daterange/{fDateStart}/{fDateEnd}")
    public AjaxResult getDateRange(@PathVariable String fDateStart, @PathVariable String fDateEnd) throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_48102.DoGetDateRange(fDateStart,fDateEnd));
    }
}

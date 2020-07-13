package com.ruoyi.project.fx_technologist.controller;

import com.ruoyi.db.mssql.DB_Ajax_DashBoard_Technologist_48102;
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
@RequestMapping("/fx_technologist")
public class TechnologistController extends BaseController
{
//    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping("/getConfirmation")
    public AjaxResult getConfirmation() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DoGetSampleConfirmation());
    }


    @GetMapping("/getBestOEM")
    public AjaxResult getBestOEM() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DoGetBestOEM());
    }

    @GetMapping("/getBestODM")
    public AjaxResult getBestODM() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DoGetBestODM());
    }

    @GetMapping("/getFinishedSampleAvgDay")
    public AjaxResult getFinishedSampleAvgDay() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DoFinishedSampleAvgDay());
    }

    @GetMapping("/getUnFinishedSampleAvgDay")
    public AjaxResult getUnFinishedSampleAvgDay() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DoUnFinishedSampleAvgDay());
    }


    /**
     *日报
     */
    @GetMapping("/DayFinishedSampleOrder")
    public AjaxResult DayFinishedSampleOrder() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DayFinishedSampleOrder());
    }

    @GetMapping("/DayNewMould")
    public AjaxResult DayNewMould() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DayNewMould());
    }

    @GetMapping("/DayFinishedSample")
    public AjaxResult DayFinishedSample() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DayFinishedSample());
    }

    @GetMapping("/DayFinishedBOM")
    public AjaxResult DayFinishedBOM() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.DayFinishedBOM());
    }


    @GetMapping("/All")
    public AjaxResult ALL() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.All());
    }

    @GetMapping("/AllDataDataRange/{fDateStart}/{fDateEnd}")
    public AjaxResult getDateRange(@PathVariable String fDateStart, @PathVariable String fDateEnd) throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.AllDataDataRange(fDateStart,fDateEnd));
    }



    @GetMapping("/getBestOEM")
    public AjaxResult getBestHuakuang() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.getBestHuakuang());
    }

    @GetMapping("/getBestODM")
    public AjaxResult getBestTijiaoxian() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.getBestTijiaoxian());
    }

    @GetMapping("/getBestODM")
    public AjaxResult getBestHunsha() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Technologist_48102.getBestHunsha());
    }





}

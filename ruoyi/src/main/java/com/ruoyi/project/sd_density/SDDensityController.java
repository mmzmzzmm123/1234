package com.ruoyi.project.sd_density;

import com.ruoyi.db.mssql.DB_Ajax_DashBoard_Density_48102;
import com.ruoyi.db.sd_mssql.DB_Ajax_DashBoard_Density_8053;
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
@RequestMapping("/sd_density")
public class SDDensityController extends BaseController
{
//    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping("/30day")
    public AjaxResult get30Day() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Density_8053.DoGet30dDensityListMap());
    }


    @GetMapping("/72h")
    public AjaxResult get72h() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Density_8053.DoGet72hDensityListMap());
    }


    @GetMapping("/group")
    public AjaxResult getGroup() throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Density_8053.DoGetGroupDensityListMap());
    }


    @GetMapping("/getDensityDataRange/{fDateStart}/{fDateEnd}")
    public AjaxResult getDateRange(@PathVariable String fDateStart, @PathVariable String fDateEnd) throws Exception
    {
        return AjaxResult.success(DB_Ajax_DashBoard_Density_8053.getDensityDataRange(fDateStart,fDateEnd));
    }

}

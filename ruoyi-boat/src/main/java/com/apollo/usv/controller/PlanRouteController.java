package com.apollo.usv.controller;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.apollo.usv.domain.PlanRoute;
import com.apollo.usv.service.IPlanRouteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划轨迹Controller
 * 
 * @author lwq
 * @date 2021-07-05
 */
@RestController
@RequestMapping("/usv/route")
public class PlanRouteController extends BaseController
{
    @Autowired
    private IPlanRouteService planRouteService;

    /**
     * 查询计划轨迹列表
     */
    @PreAuthorize("@ss.hasPermi('usv:route:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlanRoute planRoute)
    {
        startPage();
        List<PlanRoute> list = planRouteService.selectPlanRouteList(planRoute);
        return getDataTable(list);
    }

    /**
     * 导出计划轨迹列表
     */
    @PreAuthorize("@ss.hasPermi('usv:route:export')")
    @Log(title = "计划轨迹", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PlanRoute planRoute)
    {
        List<PlanRoute> list = planRouteService.selectPlanRouteList(planRoute);
        ExcelUtil<PlanRoute> util = new ExcelUtil<PlanRoute>(PlanRoute.class);
        return util.exportExcel(list, "计划轨迹数据");
    }

    /**
     * 获取计划轨迹详细信息
     */
    @PreAuthorize("@ss.hasPermi('usv:route:query')")
    @GetMapping(value = "/{routeId}")
    public AjaxResult getInfo(@PathVariable("routeId") Long routeId)
    {
        return AjaxResult.success(planRouteService.selectPlanRouteById(routeId));
    }

    /**
     * 新增计划轨迹
     */
    @PreAuthorize("@ss.hasPermi('usv:route:add')")
    @Log(title = "计划轨迹", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlanRoute planRoute)
    {
        return toAjax(planRouteService.insertPlanRoute(planRoute));
    }

    /**
     * 修改计划轨迹
     */
    @PreAuthorize("@ss.hasPermi('usv:route:edit')")
    @Log(title = "计划轨迹", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlanRoute planRoute)
    {
        return toAjax(planRouteService.updatePlanRoute(planRoute));
    }

    /**
     * 删除计划轨迹
     */
    @PreAuthorize("@ss.hasPermi('usv:route:remove')")
    @Log(title = "计划轨迹", businessType = BusinessType.DELETE)
	@DeleteMapping("/{routeIds}")
    public AjaxResult remove(@PathVariable Long[] routeIds)
    {
        return toAjax(planRouteService.deletePlanRouteByIds(routeIds));
    }
}

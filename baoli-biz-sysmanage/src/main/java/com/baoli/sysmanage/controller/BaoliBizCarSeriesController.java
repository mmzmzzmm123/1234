package com.baoli.sysmanage.controller;

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
import com.baoli.sysmanage.domain.BaoliBizCarSeries;
import com.baoli.sysmanage.service.IBaoliBizCarSeriesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车系Controller
 * 
 * @author niujs
 * @date 2024-03-19
 */
@RestController
@RequestMapping("/sysmanage/carSeries")
public class BaoliBizCarSeriesController extends BaseController
{
    @Autowired
    private IBaoliBizCarSeriesService baoliBizCarSeriesService;

    /**
     * 查询车系列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carSeries:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizCarSeries baoliBizCarSeries)
    {
        startPage();
        List<BaoliBizCarSeries> list = baoliBizCarSeriesService.selectBaoliBizCarSeriesList(baoliBizCarSeries);
        return getDataTable(list);
    }

    /**
     * 导出车系列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carSeries:export')")
    @Log(title = "车系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizCarSeries baoliBizCarSeries)
    {
        List<BaoliBizCarSeries> list = baoliBizCarSeriesService.selectBaoliBizCarSeriesList(baoliBizCarSeries);
        ExcelUtil<BaoliBizCarSeries> util = new ExcelUtil<BaoliBizCarSeries>(BaoliBizCarSeries.class);
        util.exportExcel(response, list, "车系数据");
    }

    /**
     * 获取车系详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carSeries:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizCarSeriesService.selectBaoliBizCarSeriesById(id));
    }

    /**
     * 新增车系
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carSeries:add')")
    @Log(title = "车系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizCarSeries baoliBizCarSeries)
    {
        return toAjax(baoliBizCarSeriesService.insertBaoliBizCarSeries(baoliBizCarSeries));
    }

    /**
     * 修改车系
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carSeries:edit')")
    @Log(title = "车系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizCarSeries baoliBizCarSeries)
    {
        return toAjax(baoliBizCarSeriesService.updateBaoliBizCarSeries(baoliBizCarSeries));
    }

    /**
     * 删除车系
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carSeries:remove')")
    @Log(title = "车系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizCarSeriesService.deleteBaoliBizCarSeriesByIds(ids));
    }
}

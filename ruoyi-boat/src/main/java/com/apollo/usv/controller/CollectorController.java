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
import com.apollo.usv.domain.Collector;
import com.apollo.usv.service.ICollectorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采集器Controller
 * 
 * @author lwq
 * @date 2021-07-02
 */
@RestController
@RequestMapping("/usv/collector")
public class CollectorController extends BaseController
{
    @Autowired
    private ICollectorService collectorService;

    /**
     * 查询采集器列表
     */
    @PreAuthorize("@ss.hasPermi('usv:collector:list')")
    @GetMapping("/list")
    public TableDataInfo list(Collector collector)
    {
        startPage();
        List<Collector> list = collectorService.selectCollectorList(collector);
        return getDataTable(list);
    }

    /**
     * 导出采集器列表
     */
    @PreAuthorize("@ss.hasPermi('usv:collector:export')")
    @Log(title = "采集器", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Collector collector)
    {
        List<Collector> list = collectorService.selectCollectorList(collector);
        ExcelUtil<Collector> util = new ExcelUtil<Collector>(Collector.class);
        return util.exportExcel(list, "采集器数据");
    }

    /**
     * 获取采集器详细信息
     */
    @PreAuthorize("@ss.hasPermi('usv:collector:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(collectorService.selectCollectorById(id));
    }

    /**
     * 新增采集器
     */
    @PreAuthorize("@ss.hasPermi('usv:collector:add')")
    @Log(title = "采集器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Collector collector)
    {
        return toAjax(collectorService.insertCollector(collector));
    }

    /**
     * 修改采集器
     */
    @PreAuthorize("@ss.hasPermi('usv:collector:edit')")
    @Log(title = "采集器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Collector collector)
    {
        return toAjax(collectorService.updateCollector(collector));
    }

    /**
     * 删除采集器
     */
    @PreAuthorize("@ss.hasPermi('usv:collector:remove')")
    @Log(title = "采集器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(collectorService.deleteCollectorByIds(ids));
    }
}

package com.jlt.csa.controller;

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
import com.jlt.csa.domain.FarmZone;
import com.jlt.csa.service.IFarmZoneService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 农场分区Controller
 * 
 * @author 郏磊涛
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/csa/farmzone")
public class FarmZoneController extends BaseController
{
    @Autowired
    private IFarmZoneService farmZoneService;

    /**
     * 查询农场分区列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:query')")
    @GetMapping("/list")
    public TableDataInfo list(FarmZone farmZone)
    {
        startPage();
        List<FarmZone> list = farmZoneService.selectFarmZoneList(farmZone);
        return getDataTable(list);
    }

    /**
     * 导出农场分区列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:export')")
    @Log(title = "农场分区", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FarmZone farmZone)
    {
        List<FarmZone> list = farmZoneService.selectFarmZoneList(farmZone);
        ExcelUtil<FarmZone> util = new ExcelUtil<FarmZone>(FarmZone.class);
        util.exportExcel(response, list, "农场分区数据");
    }

    /**
     * 获取农场分区详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(farmZoneService.selectFarmZoneById(id));
    }

    /**
     * 新增农场分区
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:update')")
    @Log(title = "农场分区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FarmZone farmZone)
    {
        return toAjax(farmZoneService.insertFarmZone(farmZone));
    }

    /**
     * 修改农场分区
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:update')")
    @Log(title = "农场分区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FarmZone farmZone)
    {
        return toAjax(farmZoneService.updateFarmZone(farmZone));
    }

    /**
     * 删除农场分区
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:update')")
    @Log(title = "农场分区", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(farmZoneService.deleteFarmZoneByIds(ids));
    }
}

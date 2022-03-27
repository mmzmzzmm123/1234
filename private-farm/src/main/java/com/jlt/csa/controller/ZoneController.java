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
import com.jlt.csa.domain.Zone;
import com.jlt.csa.service.IZoneService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 农场分区Controller
 * 
 * @author 郏磊涛
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/farmzone")
public class ZoneController extends BaseController
{
    @Autowired
    private IZoneService zoneService;

    /**
     * 查询农场分区列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:list')")
    @GetMapping("/list")
    public TableDataInfo list(Zone zone)
    {
        startPage();
        List<Zone> list = zoneService.selectZoneList(zone);
        return getDataTable(list);
    }

    /**
     * 导出农场分区列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:export')")
    @Log(title = "农场分区", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Zone zone)
    {
        List<Zone> list = zoneService.selectZoneList(zone);
        ExcelUtil<Zone> util = new ExcelUtil<Zone>(Zone.class);
        util.exportExcel(response, list, "农场分区数据");
    }

    /**
     * 获取农场分区详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:query')")
    @GetMapping(value = "/{code}")
    public AjaxResult getInfo(@PathVariable("code") String code)
    {
        return AjaxResult.success(zoneService.selectZoneByCode(code));
    }

    /**
     * 新增农场分区
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:add')")
    @Log(title = "农场分区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Zone zone)
    {
        return toAjax(zoneService.insertZone(zone));
    }

    /**
     * 修改农场分区
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:edit')")
    @Log(title = "农场分区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Zone zone)
    {
        return toAjax(zoneService.updateZone(zone));
    }

    /**
     * 删除农场分区
     */
    @PreAuthorize("@ss.hasPermi('csa:farmzone:remove')")
    @Log(title = "农场分区", businessType = BusinessType.DELETE)
	@DeleteMapping("/{codes}")
    public AjaxResult remove(@PathVariable String[] codes)
    {
        return toAjax(zoneService.deleteZoneByCodes(codes));
    }
}

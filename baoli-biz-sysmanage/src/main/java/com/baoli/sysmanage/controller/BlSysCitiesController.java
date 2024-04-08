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
import com.baoli.sysmanage.domain.BlSysCities;
import com.baoli.sysmanage.service.IBlSysCitiesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 行政区域地州市信息Controller
 * 
 * @author niujs
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/sysmanage/cities")
public class BlSysCitiesController extends BaseController
{
    @Autowired
    private IBlSysCitiesService blSysCitiesService;

    /**
     * 查询行政区域地州市信息列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:cities:list')")
    @GetMapping("/list")
    public TableDataInfo list(BlSysCities blSysCities)
    {
        if(blSysCities.isPageAble()){
            startPage();
        }
        List<BlSysCities> list = blSysCitiesService.selectBlSysCitiesList(blSysCities);
        return getDataTable(list);
    }

    /**
     * 导出行政区域地州市信息列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:cities:export')")
    @Log(title = "行政区域地州市信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BlSysCities blSysCities)
    {
        List<BlSysCities> list = blSysCitiesService.selectBlSysCitiesList(blSysCities);
        ExcelUtil<BlSysCities> util = new ExcelUtil<BlSysCities>(BlSysCities.class);
        util.exportExcel(response, list, "行政区域地州市信息数据");
    }

    /**
     * 获取行政区域地州市信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:cities:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(blSysCitiesService.selectBlSysCitiesById(id));
    }

    /**
     * 新增行政区域地州市信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:cities:add')")
    @Log(title = "行政区域地州市信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlSysCities blSysCities)
    {
        return toAjax(blSysCitiesService.insertBlSysCities(blSysCities));
    }

    /**
     * 修改行政区域地州市信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:cities:edit')")
    @Log(title = "行政区域地州市信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlSysCities blSysCities)
    {
        return toAjax(blSysCitiesService.updateBlSysCities(blSysCities));
    }

    /**
     * 删除行政区域地州市信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:cities:remove')")
    @Log(title = "行政区域地州市信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blSysCitiesService.deleteBlSysCitiesByIds(ids));
    }
}

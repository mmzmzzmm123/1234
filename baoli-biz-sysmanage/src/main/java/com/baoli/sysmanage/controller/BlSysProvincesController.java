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
import com.baoli.sysmanage.domain.BlSysProvinces;
import com.baoli.sysmanage.service.IBlSysProvincesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 省份信息Controller
 * 
 * @author niujs
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/sysmanage/provinces")
public class BlSysProvincesController extends BaseController
{
    @Autowired
    private IBlSysProvincesService blSysProvincesService;

    /**
     * 查询省份信息列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:provinces:list')")
    @GetMapping("/list")
    public TableDataInfo list(BlSysProvinces blSysProvinces)
    {
        if(blSysProvinces.isPageAble()){
            startPage();
        }
        List<BlSysProvinces> list = blSysProvincesService.selectBlSysProvincesList(blSysProvinces);
        return getDataTable(list);
    }

    /**
     * 导出省份信息列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:provinces:export')")
    @Log(title = "省份信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BlSysProvinces blSysProvinces)
    {
        List<BlSysProvinces> list = blSysProvincesService.selectBlSysProvincesList(blSysProvinces);
        ExcelUtil<BlSysProvinces> util = new ExcelUtil<BlSysProvinces>(BlSysProvinces.class);
        util.exportExcel(response, list, "省份信息数据");
    }

    /**
     * 获取省份信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:provinces:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(blSysProvincesService.selectBlSysProvincesById(id));
    }

    /**
     * 新增省份信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:provinces:add')")
    @Log(title = "省份信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlSysProvinces blSysProvinces)
    {
        return toAjax(blSysProvincesService.insertBlSysProvinces(blSysProvinces));
    }

    /**
     * 修改省份信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:provinces:edit')")
    @Log(title = "省份信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlSysProvinces blSysProvinces)
    {
        return toAjax(blSysProvincesService.updateBlSysProvinces(blSysProvinces));
    }

    /**
     * 删除省份信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:provinces:remove')")
    @Log(title = "省份信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blSysProvincesService.deleteBlSysProvincesByIds(ids));
    }
}

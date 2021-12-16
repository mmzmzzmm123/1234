package com.ruoyi.carpool.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.utils.uuid.UUID;
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
import com.ruoyi.carpool.domain.PDriver;
import com.ruoyi.carpool.service.IPDriverService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 司机信息Controller
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@RestController
@RequestMapping("/carpool/driver")
public class PDriverController extends BaseController
{
    @Autowired
    private IPDriverService pDriverService;

    /**
     * 查询司机信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:driver:list')")
    @GetMapping("/list")
    public TableDataInfo list(PDriver pDriver)
    {
        startPage();
        List<PDriver> list = pDriverService.selectPDriverList(pDriver);
        return getDataTable(list);
    }

    /**
     * 导出司机信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:driver:export')")
    @Log(title = "司机信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PDriver pDriver)
    {
        List<PDriver> list = pDriverService.selectPDriverList(pDriver);
        ExcelUtil<PDriver> util = new ExcelUtil<PDriver>(PDriver.class);
        util.exportExcel(response, list, "司机信息数据");
    }

    /**
     * 获取司机信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:driver:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(pDriverService.selectPDriverById(id));
    }

    /**
     * 新增司机信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:driver:add')")
    @Log(title = "司机信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PDriver pDriver)
    {
        pDriver.setDriverId(IdUtils.randomUUID());
        return toAjax(pDriverService.insertPDriver(pDriver));
    }

    /**
     * 修改司机信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:driver:edit')")
    @Log(title = "司机信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PDriver pDriver)
    {
        return toAjax(pDriverService.updatePDriver(pDriver));
    }

    /**
     * 删除司机信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:driver:remove')")
    @Log(title = "司机信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pDriverService.deletePDriverByIds(ids));
    }
}

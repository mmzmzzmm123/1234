package com.ruoyi.carpool.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.carpool.domain.PPassenger;
import com.ruoyi.carpool.service.IPPassengerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 乘客信息Controller
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@RestController
@RequestMapping("/carpool/passenger")
public class PPassengerController extends BaseController
{
    @Autowired
    private IPPassengerService pPassengerService;

    /**
     * 查询乘客信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:passenger:list')")
    @GetMapping("/list")
    public TableDataInfo list(PPassenger pPassenger)
    {
        startPage();
        List<PPassenger> list = pPassengerService.selectPPassengerList(pPassenger);
        return getDataTable(list);
    }

    /**
     * 导出乘客信息列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:passenger:export')")
    @Log(title = "乘客信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PPassenger pPassenger)
    {
        List<PPassenger> list = pPassengerService.selectPPassengerList(pPassenger);
        ExcelUtil<PPassenger> util = new ExcelUtil<PPassenger>(PPassenger.class);
        util.exportExcel(response, list, "乘客信息数据");
    }

    /**
     * 获取乘客信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:passenger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(pPassengerService.selectPPassengerById(id));
    }

    /**
     * 新增乘客信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:passenger:add')")
    @Log(title = "乘客信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PPassenger pPassenger)
    {
        pPassenger.setCustId("passenger_"+IdUtils.randomUUID());
        return toAjax(pPassengerService.insertPPassenger(pPassenger));
    }

    /**
     * 修改乘客信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:passenger:edit')")
    @Log(title = "乘客信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PPassenger pPassenger)
    {
        return toAjax(pPassengerService.updatePPassenger(pPassenger));
    }

    /**
     * 删除乘客信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:passenger:remove')")
    @Log(title = "乘客信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pPassengerService.deletePPassengerByIds(ids));
    }


    /**
     * 获取乘客信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:passenger:query')")
    @PostMapping(value = "/queryUserInfo")
    public AjaxResult queryUserInfo(@RequestBody PPassenger pPassenger)
    {
        return AjaxResult.success(pPassengerService.queryUserInfo(pPassenger));
    }


}

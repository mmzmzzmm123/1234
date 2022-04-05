package com.jlt.csa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jlt.csa.domain.Garden;
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
import com.jlt.csa.domain.Farmer;
import com.jlt.csa.service.IFarmerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 农场会员Controller
 * 
 * @author JiaLeitao
 * @date 2022-04-01
 */
@RestController
@RequestMapping("/csa/farmer")
public class FarmerController extends BaseController
{
    @Autowired
    private IFarmerService farmerService;

    /**
     * 查询农场会员列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Farmer farmer)
    {
        startPage();
        List<Farmer> list = farmerService.selectFarmerList(farmer);
        return getDataTable(list);
    }

    /**
     * 导出农场会员列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:export')")
    @Log(title = "农场会员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Farmer farmer)
    {
        List<Farmer> list = farmerService.selectFarmerList(farmer);
        ExcelUtil<Farmer> util = new ExcelUtil<Farmer>(Farmer.class);
        util.exportExcel(response, list, "农场会员数据");
    }

    /**
     * 获取农场会员详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:query')")
    @GetMapping(value = "/{farmerId}")
    public AjaxResult getInfo(@PathVariable("farmerId") Long farmerId)
    {
        return AjaxResult.success(farmerService.selectFarmerByFarmerId(farmerId));
    }

    /**
     * 获取带菜地信息的地主详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:query')")
    @GetMapping(value = "/garden/{farmerId}")
    public AjaxResult getInfoWithGarden(@PathVariable("farmerId") Long farmerId)
    {
        Farmer farmer = farmerService.selectFarmerWithGardenById(farmerId);
        if (farmer.getGarden() == null)
            farmer.setGarden(new Garden());

        return AjaxResult.success(farmer);
    }

    /**
     * 新增农场会员
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:add')")
    @Log(title = "农场会员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Farmer farmer)
    {
        return toAjax(farmerService.insertFarmer(farmer));
    }

    /**
     * 修改农场会员
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:edit')")
    @Log(title = "农场会员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Farmer farmer)
    {
        return toAjax(farmerService.updateFarmer(farmer));
    }

    /**
     * 删除农场会员
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:remove')")
    @Log(title = "农场会员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{farmerIds}")
    public AjaxResult remove(@PathVariable Long[] farmerIds)
    {
        return toAjax(farmerService.deleteFarmerByFarmerIds(farmerIds));
    }
}

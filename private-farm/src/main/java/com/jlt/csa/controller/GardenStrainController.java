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
import com.jlt.csa.domain.GardenStrain;
import com.jlt.csa.service.IGardenStrainService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 菜地品种Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/strain")
public class GardenStrainController extends BaseController
{
    @Autowired
    private IGardenStrainService gardenStrainService;

    /**
     * 查询菜地品种列表
     */
    @PreAuthorize("@ss.hasPermi('csa:strain:list')")
    @GetMapping("/list")
    public TableDataInfo list(GardenStrain gardenStrain)
    {
        startPage();
        List<GardenStrain> list = gardenStrainService.selectGardenStrainList(gardenStrain);
        return getDataTable(list);
    }

    /**
     * 导出菜地品种列表
     */
    @PreAuthorize("@ss.hasPermi('csa:strain:export')")
    @Log(title = "菜地品种", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GardenStrain gardenStrain)
    {
        List<GardenStrain> list = gardenStrainService.selectGardenStrainList(gardenStrain);
        ExcelUtil<GardenStrain> util = new ExcelUtil<GardenStrain>(GardenStrain.class);
        util.exportExcel(response, list, "菜地品种数据");
    }

    /**
     * 获取菜地品种详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:strain:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gardenStrainService.selectGardenStrainById(id));
    }

    /**
     * 新增菜地品种
     */
    @PreAuthorize("@ss.hasPermi('csa:strain:add')")
    @Log(title = "菜地品种", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GardenStrain gardenStrain)
    {
        return toAjax(gardenStrainService.insertGardenStrain(gardenStrain));
    }

    /**
     * 修改菜地品种
     */
    @PreAuthorize("@ss.hasPermi('csa:strain:edit')")
    @Log(title = "菜地品种", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GardenStrain gardenStrain)
    {
        return toAjax(gardenStrainService.updateGardenStrain(gardenStrain));
    }

    /**
     * 删除菜地品种
     */
    @PreAuthorize("@ss.hasPermi('csa:strain:remove')")
    @Log(title = "菜地品种", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gardenStrainService.deleteGardenStrainByIds(ids));
    }
}

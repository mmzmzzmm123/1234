package com.jlt.csa.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.jlt.csa.domain.Garden;
import com.jlt.csa.service.IGardenService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 菜地划分Controller
 *
 * @author 郏磊涛
 * @date 2022-03-29
 */
@RestController
@RequestMapping("/csa/garden")
public class GardenController extends BaseController
{
    @Autowired
    private IGardenService gardenService;

    /**
     * 查询菜地划分列表
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:query')")
    @GetMapping("/list")
    public TableDataInfo list(Garden garden)
    {
        startPage();
        List<Garden> list = gardenService.selectGardenList(garden);
        return getDataTable(list);
    }

    /**
     * 按代码筛选可绑定的菜地列表
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:query')")
    @GetMapping({"/cansell/{code}", "/cansell/"})
    public TableDataInfo listCanSell(@PathVariable(value = "code", required = false) String code)
    {
        Garden garden = new Garden();
        garden.setCode(code);
        garden.setIsCompleted("Y");
        garden.setIsSelled("N");
        garden.setStatus("0");

        List<Garden> list = gardenService.selectGardenList(garden);

        return getDataTable(list);
    }

    /**
     * 导出菜地划分列表
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:export')")
    @Log(title = "菜地划分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Garden garden)
    {
        List<Garden> list = gardenService.selectGardenList(garden);
        ExcelUtil<Garden> util = new ExcelUtil<Garden>(Garden.class);
        util.exportExcel(response, list, "菜地划分数据");
    }

    /**
     * 获取菜地划分详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gardenService.selectGardenById(id));
    }

    /**
     * 新增菜地划分
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:update')")
    @Log(title = "菜地划分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Garden garden, @RequestBody Map<String, Object> request)
    {
        int amount = (int) request.get("amount");
        garden.setCreateBy(getUsername());
        garden.setCreateTime(DateUtils.getNowDate());

        if (amount == 1) {
            return toAjax(gardenService.insertGarden(garden));
        } else {
            return toAjax(gardenService.insertGardens(garden, amount));
        }
    }

    /**
     * 修改菜地划分
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:update')")
    @Log(title = "菜地划分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Garden garden)
    {
        garden.setUpdateBy(getUsername());
        garden.setUpdateTime(DateUtils.getNowDate());
        return toAjax(gardenService.updateGarden(garden));
    }

    /**
     * 批量更新菜地状态
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:update')")
    @Log(title = "菜地划分", businessType = BusinessType.UPDATE)
    @PutMapping("/{ids}")
    public AjaxResult updateStatus(@RequestBody Garden garden, @PathVariable Long[] ids)
    {
        garden.setUpdateBy(getUsername());
        garden.setUpdateTime(DateUtils.getNowDate());
        return toAjax(gardenService.updateGardenStatus(garden, ids));
    }

    /**
     * 删除菜地划分
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:update')")
    @Log(title = "菜地划分", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gardenService.deleteGardenByIds(ids));
    }
}

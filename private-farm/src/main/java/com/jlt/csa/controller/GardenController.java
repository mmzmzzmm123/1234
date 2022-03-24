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
import com.jlt.csa.domain.Garden;
import com.jlt.csa.service.IGardenService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 菜地划分Controller
 * 
 * @author 郏磊涛
 * @date 2022-03-24
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
    @PreAuthorize("@ss.hasPermi('csa:garden:list')")
    @GetMapping("/list")
    public TableDataInfo list(Garden garden)
    {
        startPage();
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
    @PreAuthorize("@ss.hasPermi('csa:garden:add')")
    @Log(title = "菜地划分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Garden garden)
    {
        return toAjax(gardenService.insertGarden(garden));
    }

    /**
     * 修改菜地划分
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:edit')")
    @Log(title = "菜地划分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Garden garden)
    {
        return toAjax(gardenService.updateGarden(garden));
    }

    /**
     * 删除菜地划分
     */
    @PreAuthorize("@ss.hasPermi('csa:garden:remove')")
    @Log(title = "菜地划分", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gardenService.deleteGardenByIds(ids));
    }
}

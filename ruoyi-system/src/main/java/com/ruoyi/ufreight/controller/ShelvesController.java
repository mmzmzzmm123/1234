package com.ruoyi.ufreight.controller;

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
import com.ruoyi.ufreight.domain.Shelves;
import com.ruoyi.ufreight.service.IShelvesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库货架Controller
 * 
 * @author derek
 * @date 2023-11-20
 */
@RestController
@RequestMapping("/ufreight/shelves")
public class ShelvesController extends BaseController
{
    @Autowired
    private IShelvesService shelvesService;

    /**
     * 查询仓库货架列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:shelves:list')")
    @GetMapping("/list")
    public TableDataInfo list(Shelves shelves)
    {
        startPage();
        List<Shelves> list = shelvesService.selectShelvesList(shelves);
        return getDataTable(list);
    }

    /**
     * 导出仓库货架列表
     */
    @PreAuthorize("@ss.hasPermi('ufreight:shelves:export')")
    @Log(title = "仓库货架", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Shelves shelves)
    {
        List<Shelves> list = shelvesService.selectShelvesList(shelves);
        ExcelUtil<Shelves> util = new ExcelUtil<Shelves>(Shelves.class);
        util.exportExcel(response, list, "仓库货架数据");
    }

    /**
     * 获取仓库货架详细信息
     */
    @PreAuthorize("@ss.hasPermi('ufreight:shelves:query')")
    @GetMapping(value = "/{shelfId}")
    public AjaxResult getInfo(@PathVariable("shelfId") Long shelfId)
    {
        return success(shelvesService.selectShelvesByShelfId(shelfId));
    }

    /**
     * 新增仓库货架
     */
    @PreAuthorize("@ss.hasPermi('ufreight:shelves:add')")
    @Log(title = "仓库货架", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Shelves shelves)
    {
        return toAjax(shelvesService.insertShelves(shelves));
    }

    /**
     * 修改仓库货架
     */
    @PreAuthorize("@ss.hasPermi('ufreight:shelves:edit')")
    @Log(title = "仓库货架", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Shelves shelves)
    {
        return toAjax(shelvesService.updateShelves(shelves));
    }

    /**
     * 删除仓库货架
     */
    @PreAuthorize("@ss.hasPermi('ufreight:shelves:remove')")
    @Log(title = "仓库货架", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shelfIds}")
    public AjaxResult remove(@PathVariable Long[] shelfIds)
    {
        return toAjax(shelvesService.deleteShelvesByShelfIds(shelfIds));
    }
}

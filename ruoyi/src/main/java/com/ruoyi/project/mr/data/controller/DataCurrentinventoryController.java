package com.ruoyi.project.mr.data.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.mr.data.domain.DataCurrentinventory;
import com.ruoyi.project.mr.data.service.IDataCurrentinventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 当前库存存储Controller
 *
 * @author mr
 * @date 2020-01-14
 */
@RestController
@RequestMapping("/data/currentinventory")
public class DataCurrentinventoryController extends BaseController
{
    @Autowired
    private IDataCurrentinventoryService dataCurrentinventoryService;

    /**
     * 查询当前库存存储列表
     */
    @PreAuthorize("@ss.hasPermi('data:currentinventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataCurrentinventory dataCurrentinventory)
    {
        startPage();
        List<DataCurrentinventory> list = dataCurrentinventoryService.selectDataCurrentinventorySum(dataCurrentinventory);
        return getDataTable(list);
    }

    /**
     * 导出当前库存存储列表
     */
    @PreAuthorize("@ss.hasPermi('data:currentinventory:export')")
    @Log(title = "当前库存存储", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataCurrentinventory dataCurrentinventory)
    {
        List<DataCurrentinventory> list = dataCurrentinventoryService.selectDataCurrentinventoryList(dataCurrentinventory);
        ExcelUtil<DataCurrentinventory> util = new ExcelUtil<DataCurrentinventory>(DataCurrentinventory.class);
        return util.exportExcel(list, "currentinventory");
    }

    /**
     * 获取当前库存存储详细信息
     */
    @PreAuthorize("@ss.hasPermi('data:currentinventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(dataCurrentinventoryService.selectDataCurrentinventoryById(id));
    }

    /**
     * 新增当前库存存储
     */
    @PreAuthorize("@ss.hasPermi('data:currentinventory:add')")
    @Log(title = "当前库存存储", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataCurrentinventory dataCurrentinventory)
    {
        return toAjax(dataCurrentinventoryService.insertDataCurrentinventory(dataCurrentinventory));
    }

    /**
     * 修改当前库存存储
     */
    @PreAuthorize("@ss.hasPermi('data:currentinventory:edit')")
    @Log(title = "当前库存存储", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataCurrentinventory dataCurrentinventory)
    {
        return toAjax(dataCurrentinventoryService.updateDataCurrentinventory(dataCurrentinventory));
    }

    /**
     * 删除当前库存存储
     */
    @PreAuthorize("@ss.hasPermi('data:currentinventory:remove')")
    @Log(title = "当前库存存储", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(dataCurrentinventoryService.deleteDataCurrentinventoryByIds(ids));
    }
}

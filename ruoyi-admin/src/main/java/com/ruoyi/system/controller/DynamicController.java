package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.Dynamic;
import com.ruoyi.system.service.IDynamicService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 动态管理Controller
 * 
 * @author ruoyi
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/system/dynamic")
public class DynamicController extends BaseController
{
    @Autowired
    private IDynamicService dynamicService;

    /**
     * 查询动态管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:dynamic:list')")
    @GetMapping("/list")
    public TableDataInfo list(Dynamic dynamic)
    {
        startPage();
        List<Dynamic> list = dynamicService.selectDynamicList(dynamic);
        return getDataTable(list);
    }

    /**
     * 导出动态管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:dynamic:export')")
    @Log(title = "动态管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Dynamic dynamic)
    {
        List<Dynamic> list = dynamicService.selectDynamicList(dynamic);
        ExcelUtil<Dynamic> util = new ExcelUtil<Dynamic>(Dynamic.class);
        util.exportExcel(response, list, "动态管理数据");
    }

    /**
     * 获取动态管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dynamic:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dynamicService.selectDynamicById(id));
    }

    /**
     * 新增动态管理
     */
    @PreAuthorize("@ss.hasPermi('system:dynamic:add')")
    @Log(title = "动态管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Dynamic dynamic)
    {
        return toAjax(dynamicService.insertDynamic(dynamic));
    }

    /**
     * 修改动态管理
     */
    @PreAuthorize("@ss.hasPermi('system:dynamic:edit')")
    @Log(title = "动态管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Dynamic dynamic)
    {
        return toAjax(dynamicService.updateDynamic(dynamic));
    }

    /**
     * 删除动态管理
     */
    @PreAuthorize("@ss.hasPermi('system:dynamic:remove')")
    @Log(title = "动态管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dynamicService.deleteDynamicByIds(ids));
    }
}

package com.ruoyi.project.mr.base.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.mr.base.domain.DataIdiom;
import com.ruoyi.project.mr.base.service.IDataIdiomService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 成语存放Controller
 *
 * @author mr
 * @date 2020-03-03
 */
@RestController
@RequestMapping("/base/idiom")
public class DataIdiomController extends BaseController
{
    @Autowired
    private IDataIdiomService dataIdiomService;

    /**
     * 查询成语存放列表
     */
    @PreAuthorize("@ss.hasPermi('base:idiom:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataIdiom dataIdiom)
    {
        startPage();
        List<DataIdiom> list = dataIdiomService.selectDataIdiomList(dataIdiom);
        return getDataTable(list);
    }

    /**
     * 导出成语存放列表
     */
    @PreAuthorize("@ss.hasPermi('base:idiom:export')")
    @Log(title = "成语存放", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DataIdiom dataIdiom)
    {
        List<DataIdiom> list = dataIdiomService.selectDataIdiomList(dataIdiom);
        ExcelUtil<DataIdiom> util = new ExcelUtil<DataIdiom>(DataIdiom.class);
        return util.exportExcel(list, "idiom");
    }

    /**
     * 获取成语存放详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:idiom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dataIdiomService.selectDataIdiomById(id));
    }

    /**
     * 新增成语存放
     */
    @PreAuthorize("@ss.hasPermi('base:idiom:add')")
    @Log(title = "成语存放", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataIdiom dataIdiom)
    {
        dataIdiom.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dataIdiomService.insertDataIdiom(dataIdiom));
    }

    /**
     * 修改成语存放
     */
    @PreAuthorize("@ss.hasPermi('base:idiom:edit')")
    @Log(title = "成语存放", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataIdiom dataIdiom)
    {
        return toAjax(dataIdiomService.updateDataIdiom(dataIdiom));
    }

    /**
     * 删除成语存放
     */
    @PreAuthorize("@ss.hasPermi('base:idiom:remove')")
    @Log(title = "成语存放", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dataIdiomService.deleteDataIdiomByIds(ids));
    }
}

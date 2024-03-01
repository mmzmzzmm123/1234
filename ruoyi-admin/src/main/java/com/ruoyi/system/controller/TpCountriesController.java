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
import com.ruoyi.system.domain.TpCountries;
import com.ruoyi.system.service.ITpCountriesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 全球国家信息管理Controller
 * 
 * @author carol
 * @date 2024-03-01
 */
@RestController
@RequestMapping("/api/countries")
public class TpCountriesController extends BaseController
{
    @Autowired
    private ITpCountriesService tpCountriesService;

    /**
     * 查询全球国家信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:countries:list')")
    @GetMapping("/list")
    public TableDataInfo list(TpCountries tpCountries)
    {
        startPage();
        List<TpCountries> list = tpCountriesService.selectTpCountriesList(tpCountries);
        return getDataTable(list);
    }

    /**
     * 导出全球国家信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:countries:export')")
    @Log(title = "全球国家信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TpCountries tpCountries)
    {
        List<TpCountries> list = tpCountriesService.selectTpCountriesList(tpCountries);
        ExcelUtil<TpCountries> util = new ExcelUtil<TpCountries>(TpCountries.class);
        util.exportExcel(response, list, "全球国家信息管理数据");
    }

    /**
     * 获取全球国家信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('api:countries:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tpCountriesService.selectTpCountriesById(id));
    }

    /**
     * 新增全球国家信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:countries:add')")
    @Log(title = "全球国家信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TpCountries tpCountries)
    {
        return toAjax(tpCountriesService.insertTpCountries(tpCountries));
    }

    /**
     * 修改全球国家信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:countries:edit')")
    @Log(title = "全球国家信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TpCountries tpCountries)
    {
        return toAjax(tpCountriesService.updateTpCountries(tpCountries));
    }

    /**
     * 删除全球国家信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:countries:remove')")
    @Log(title = "全球国家信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tpCountriesService.deleteTpCountriesByIds(ids));
    }
}

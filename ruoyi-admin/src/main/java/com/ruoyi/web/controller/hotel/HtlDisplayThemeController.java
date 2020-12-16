package com.ruoyi.web.controller.hotel;

import java.util.List;
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
import com.ruoyi.hotel.domain.HtlDisplayTheme;
import com.ruoyi.hotel.service.IHtlDisplayThemeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主题配置Controller
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@RestController
@RequestMapping("/hotel/displayTheme")
public class HtlDisplayThemeController extends BaseController
{
    @Autowired
    private IHtlDisplayThemeService htlDisplayThemeService;

    /**
     * 查询主题配置列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:displayTheme:list')")
    @GetMapping("/list")
    public TableDataInfo list(HtlDisplayTheme htlDisplayTheme)
    {
        startPage();
        List<HtlDisplayTheme> list = htlDisplayThemeService.selectHtlDisplayThemeList(htlDisplayTheme);
        return getDataTable(list);
    }

    /**
     * 导出主题配置列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:displayTheme:export')")
    @Log(title = "主题配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HtlDisplayTheme htlDisplayTheme)
    {
        List<HtlDisplayTheme> list = htlDisplayThemeService.selectHtlDisplayThemeList(htlDisplayTheme);
        ExcelUtil<HtlDisplayTheme> util = new ExcelUtil<HtlDisplayTheme>(HtlDisplayTheme.class);
        return util.exportExcel(list, "displayTheme");
    }

    /**
     * 获取主题配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:displayTheme:query')")
    @GetMapping(value = "/{hotelId}")
    public AjaxResult getInfo(@PathVariable("hotelId") Long hotelId)
    {
        return AjaxResult.success(htlDisplayThemeService.selectHtlDisplayThemeById(hotelId));
    }

    /**
     * 新增主题配置
     */
    @PreAuthorize("@ss.hasPermi('hotel:displayTheme:add')")
    @Log(title = "主题配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HtlDisplayTheme htlDisplayTheme)
    {
        return toAjax(htlDisplayThemeService.insertHtlDisplayTheme(htlDisplayTheme));
    }

    /**
     * 修改主题配置
     */
    @PreAuthorize("@ss.hasPermi('hotel:displayTheme:edit')")
    @Log(title = "主题配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HtlDisplayTheme htlDisplayTheme)
    {
        return toAjax(htlDisplayThemeService.updateHtlDisplayTheme(htlDisplayTheme));
    }

    /**
     * 删除主题配置
     */
    @PreAuthorize("@ss.hasPermi('hotel:displayTheme:remove')")
    @Log(title = "主题配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hotelIds}")
    public AjaxResult remove(@PathVariable Long[] hotelIds)
    {
        return toAjax(htlDisplayThemeService.deleteHtlDisplayThemeByIds(hotelIds));
    }
}

package com.baoli.sysmanage.controller;

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
import com.baoli.sysmanage.domain.BlSysCarousel;
import com.baoli.sysmanage.service.IBlSysCarouselService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图管理Controller
 * 
 * @author niujs
 * @date 2024-04-10
 */
@RestController
@RequestMapping("/sysmanage/carousel")
public class BlSysCarouselController extends BaseController
{
    @Autowired
    private IBlSysCarouselService blSysCarouselService;

    /**
     * 查询轮播图管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carousel:list')")
    @GetMapping("/list")
    public TableDataInfo list(BlSysCarousel blSysCarousel)
    {
        startPage();
        List<BlSysCarousel> list = blSysCarouselService.selectBlSysCarouselList(blSysCarousel);
        return getDataTable(list);
    }

    /**
     * 导出轮播图管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carousel:export')")
    @Log(title = "轮播图管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BlSysCarousel blSysCarousel)
    {
        List<BlSysCarousel> list = blSysCarouselService.selectBlSysCarouselList(blSysCarousel);
        ExcelUtil<BlSysCarousel> util = new ExcelUtil<BlSysCarousel>(BlSysCarousel.class);
        util.exportExcel(response, list, "轮播图管理数据");
    }

    /**
     * 获取轮播图管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carousel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(blSysCarouselService.selectBlSysCarouselById(id));
    }

    /**
     * 新增轮播图管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carousel:add')")
    @Log(title = "轮播图管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlSysCarousel blSysCarousel)
    {
        return toAjax(blSysCarouselService.insertBlSysCarousel(blSysCarousel));
    }

    /**
     * 修改轮播图管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carousel:edit')")
    @Log(title = "轮播图管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlSysCarousel blSysCarousel)
    {
        return toAjax(blSysCarouselService.updateBlSysCarousel(blSysCarousel));
    }

    /**
     * 删除轮播图管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carousel:remove')")
    @Log(title = "轮播图管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blSysCarouselService.deleteBlSysCarouselByIds(ids));
    }
}

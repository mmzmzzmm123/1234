package com.geek.system.controller;

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
import com.geek.common.annotation.Log;
import com.geek.common.core.controller.BaseController;
import com.geek.common.core.domain.AjaxResult;
import com.geek.common.enums.BusinessType;
import com.geek.system.domain.SysBanner;
import com.geek.system.service.ISysBannerService;
import com.geek.common.utils.poi.ExcelUtil;
import com.geek.common.core.page.TableDataInfo;

/**
 * banner配置Controller
 * 
 * @author xuek
 * @date 2023-03-30
 */
@RestController
@RequestMapping("/system/banner")
public class SysBannerController extends BaseController{

    @Autowired
    private ISysBannerService sysBannerService;

    /**
     * 查询banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBanner sysBanner){

        startPage();
        List<SysBanner> list = sysBannerService.selectSysBannerList(sysBanner);
        return getDataTable(list);
    }

    /**
     * 导出banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:banner:export')")
    @Log(title = "banner配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBanner sysBanner){

        List<SysBanner> list = sysBannerService.selectSysBannerList(sysBanner);
        ExcelUtil<SysBanner> util = new ExcelUtil<SysBanner>(SysBanner.class);
        util.exportExcel(response, list, "banner配置数据");
    }

    /**
     * 获取banner配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){

        return success(sysBannerService.selectSysBannerById(id));
    }

    /**
     * 新增banner配置
     */
    @PreAuthorize("@ss.hasPermi('system:banner:add')")
    @Log(title = "banner配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysBanner sysBanner){

        return toAjax(sysBannerService.insertSysBanner(sysBanner));
    }

    /**
     * 修改banner配置
     */
    @PreAuthorize("@ss.hasPermi('system:banner:edit')")
    @Log(title = "banner配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBanner sysBanner){

        return toAjax(sysBannerService.updateSysBanner(sysBanner));
    }

    /**
     * 删除banner配置
     */
    @PreAuthorize("@ss.hasPermi('system:banner:remove')")
    @Log(title = "banner配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){

        return toAjax(sysBannerService.deleteSysBannerByIds(ids));
    }
}

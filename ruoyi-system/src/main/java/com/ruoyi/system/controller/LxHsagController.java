package com.ruoyi.system.controller;

import java.sql.Date;
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
import com.ruoyi.system.domain.LxHsag;
import com.ruoyi.system.service.ILxHsagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 信号与系统Controller
 * 
 * @author gongyu
 * @date 2022-06-19
 */
@RestController
@RequestMapping("/invest/hsag")
public class LxHsagController extends BaseController
{
    @Autowired
    private ILxHsagService lxHsagService;

    /**
     * 查询信号与系统列表
     */
    @PreAuthorize("@ss.hasPermi('invest:hsag:list')")
    @GetMapping("/list")
    public TableDataInfo list(LxHsag lxHsag)
    {
        startPage();
        List<LxHsag> list = lxHsagService.selectLxHsagList(lxHsag);
        return getDataTable(list);
    }

    /**
     * 导出信号与系统列表
     */
    @PreAuthorize("@ss.hasPermi('invest:hsag:export')")
    @Log(title = "信号与系统", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LxHsag lxHsag)
    {
        List<LxHsag> list = lxHsagService.selectLxHsagList(lxHsag);
        ExcelUtil<LxHsag> util = new ExcelUtil<LxHsag>(LxHsag.class);
        util.exportExcel(response, list, "信号与系统数据");
    }

    /**
     * 获取信号与系统详细信息
     */
    @PreAuthorize("@ss.hasPermi('invest:hsag:query')")
    @GetMapping(value = "/{sj}")
    public AjaxResult getInfo(@PathVariable("sj") Date sj)
    {
        return AjaxResult.success(lxHsagService.selectLxHsagBySj(sj));
    }

    /**
     * 新增信号与系统
     */
    @PreAuthorize("@ss.hasPermi('invest:hsag:add')")
    @Log(title = "信号与系统", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LxHsag lxHsag)
    {
        return toAjax(lxHsagService.insertLxHsag(lxHsag));
    }

    /**
     * 修改信号与系统
     */
    @PreAuthorize("@ss.hasPermi('invest:hsag:edit')")
    @Log(title = "信号与系统", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LxHsag lxHsag)
    {
        return toAjax(lxHsagService.updateLxHsag(lxHsag));
    }

    /**
     * 删除信号与系统
     */
    @PreAuthorize("@ss.hasPermi('invest:hsag:remove')")
    @Log(title = "信号与系统", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sjs}")
    public AjaxResult remove(@PathVariable Date[] sjs)
    {
        return toAjax(lxHsagService.deleteLxHsagBySjs(sjs));
    }
}

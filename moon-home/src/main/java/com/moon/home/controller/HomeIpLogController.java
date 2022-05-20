package com.moon.home.controller;

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
import com.moon.home.domain.HomeIpLog;
import com.moon.home.service.IHomeIpLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * logController
 * 
 * @author goo
 * @date 2022-05-19
 */
@RestController
@RequestMapping("/home/log")
public class HomeIpLogController extends BaseController
{
    @Autowired
    private IHomeIpLogService homeIpLogService;

    /**
     * 查询log列表
     */
    @PreAuthorize("@ss.hasPermi('home:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomeIpLog homeIpLog)
    {
        startPage();
        List<HomeIpLog> list = homeIpLogService.selectHomeIpLogList(homeIpLog);
        return getDataTable(list);
    }

    /**
     * 导出log列表
     */
    @PreAuthorize("@ss.hasPermi('home:log:export')")
    @Log(title = "log", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomeIpLog homeIpLog)
    {
        List<HomeIpLog> list = homeIpLogService.selectHomeIpLogList(homeIpLog);
        ExcelUtil<HomeIpLog> util = new ExcelUtil<HomeIpLog>(HomeIpLog.class);
        util.exportExcel(response, list, "log数据");
    }

    /**
     * 获取log详细信息
     */
    @PreAuthorize("@ss.hasPermi('home:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(homeIpLogService.selectHomeIpLogById(id));
    }

    /**
     * 新增log
     */
    @PreAuthorize("@ss.hasPermi('home:log:add')")
    @Log(title = "log", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomeIpLog homeIpLog)
    {
        return toAjax(homeIpLogService.insertHomeIpLog(homeIpLog));
    }

    /**
     * 修改log
     */
    @PreAuthorize("@ss.hasPermi('home:log:edit')")
    @Log(title = "log", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomeIpLog homeIpLog)
    {
        return toAjax(homeIpLogService.updateHomeIpLog(homeIpLog));
    }

    /**
     * 删除log
     */
    @PreAuthorize("@ss.hasPermi('home:log:remove')")
    @Log(title = "log", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(homeIpLogService.deleteHomeIpLogByIds(ids));
    }
}

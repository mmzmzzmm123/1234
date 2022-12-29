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
import com.ruoyi.system.domain.AppIosration;
import com.ruoyi.system.service.IAppIosrationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@RestController
@RequestMapping("/system/iosRation")
public class AppIosrationController extends BaseController
{
    @Autowired
    private IAppIosrationService appIosrationService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:iosRation:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppIosration appIosration)
    {
        startPage();
        List<AppIosration> list = appIosrationService.selectAppIosrationList(appIosration);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:iosRation:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppIosration appIosration)
    {
        List<AppIosration> list = appIosrationService.selectAppIosrationList(appIosration);
        ExcelUtil<AppIosration> util = new ExcelUtil<AppIosration>(AppIosration.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:iosRation:query')")
    @GetMapping(value = "/{mId}")
    public AjaxResult getInfo(@PathVariable("mId") Long mId)
    {
        return success(appIosrationService.selectAppIosrationByMId(mId));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:iosRation:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppIosration appIosration)
    {
        return toAjax(appIosrationService.insertAppIosration(appIosration));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:iosRation:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppIosration appIosration)
    {
        return toAjax(appIosrationService.updateAppIosration(appIosration));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:iosRation:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{mIds}")
    public AjaxResult remove(@PathVariable Long[] mIds)
    {
        return toAjax(appIosrationService.deleteAppIosrationByMIds(mIds));
    }
}

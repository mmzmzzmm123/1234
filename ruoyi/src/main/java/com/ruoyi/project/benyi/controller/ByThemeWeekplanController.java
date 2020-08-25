package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.SchoolCommon;
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
import com.ruoyi.project.benyi.domain.ByThemeWeekplan;
import com.ruoyi.project.benyi.service.IByThemeWeekplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合周计划Controller
 *
 * @author tsbz
 * @date 2020-08-25
 */
@RestController
@RequestMapping("/benyi/themeweekplan")
public class ByThemeWeekplanController extends BaseController
{
    @Autowired
    private IByThemeWeekplanService byThemeWeekplanService;
    @Autowired
    private SchoolCommon schoolCommon;

/**
 * 查询主题整合周计划列表
 */
@PreAuthorize("@ss.hasPermi('benyi:thememonthplan:list')")
@GetMapping("/list")
        public TableDataInfo list(ByThemeWeekplan byThemeWeekplan)
    {
        startPage();
        List<ByThemeWeekplan> list = byThemeWeekplanService.selectByThemeWeekplanList(byThemeWeekplan);
        return getDataTable(list);
    }
    
    /**
     * 导出主题整合周计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:export')")
    @Log(title = "主题整合周计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeWeekplan byThemeWeekplan)
    {
        List<ByThemeWeekplan> list = byThemeWeekplanService.selectByThemeWeekplanList(byThemeWeekplan);
        ExcelUtil<ByThemeWeekplan> util = new ExcelUtil<ByThemeWeekplan>(ByThemeWeekplan.class);
        return util.exportExcel(list, "weekplan");
    }

    /**
     * 获取主题整合周计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(byThemeWeekplanService.selectByThemeWeekplanById(id));
    }

    /**
     * 新增主题整合周计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:add')")
    @Log(title = "主题整合周计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeWeekplan byThemeWeekplan)
    {
        String uuid=schoolCommon.getUuid();
        byThemeWeekplan.setId(uuid);
        byThemeWeekplan.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(byThemeWeekplanService.insertByThemeWeekplan(byThemeWeekplan));
    }

    /**
     * 修改主题整合周计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:edit')")
    @Log(title = "主题整合周计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeWeekplan byThemeWeekplan)
    {
        return toAjax(byThemeWeekplanService.updateByThemeWeekplan(byThemeWeekplan));
    }

    /**
     * 删除主题整合周计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:remove')")
    @Log(title = "主题整合周计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(byThemeWeekplanService.deleteByThemeWeekplanByIds(ids));
    }
}

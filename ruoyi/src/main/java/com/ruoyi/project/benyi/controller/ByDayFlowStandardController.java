package com.ruoyi.project.benyi.controller;

import java.util.Date;
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
import com.ruoyi.project.benyi.domain.ByDayFlowStandard;
import com.ruoyi.project.benyi.service.IByDayFlowStandardService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 一日流程标准Controller
 * 
 * @author tsbz
 * @date 2020-05-18
 */
@RestController
@RequestMapping("/benyi/dayflow/biaozhun/standard")
public class ByDayFlowStandardController extends BaseController
{
    @Autowired
    private IByDayFlowStandardService byDayFlowStandardService;

    /**
     * 查询一日流程标准列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:standard:list')"+ "||@ss.hasPermi('benyi:dayflowmanger:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayFlowStandard byDayFlowStandard)
    {
        startPage();
        List<ByDayFlowStandard> list = byDayFlowStandardService.selectByDayFlowStandardList(byDayFlowStandard);
        return getDataTable(list);
    }

    /**
     * 导出一日流程标准列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:standard:export')")
    @Log(title = "一日流程标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayFlowStandard byDayFlowStandard)
    {
        List<ByDayFlowStandard> list = byDayFlowStandardService.selectByDayFlowStandardList(byDayFlowStandard);
        ExcelUtil<ByDayFlowStandard> util = new ExcelUtil<ByDayFlowStandard>(ByDayFlowStandard.class);
        return util.exportExcel(list, "standard");
    }

    /**
     * 获取一日流程标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:standard:query')")
    @GetMapping(value = "/{sid}")
    public AjaxResult getInfo(@PathVariable("sid") Long sid)
    {
        return AjaxResult.success(byDayFlowStandardService.selectByDayFlowStandardById(sid));
    }

    /**
     * 新增一日流程标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:standard:add')")
    @Log(title = "一日流程标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayFlowStandard byDayFlowStandard)
    {
        byDayFlowStandard.setCreateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        //byDayFlowStandard.setUpdateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayFlowStandard.setCreatetime(new Date());
        //byDayFlowStandard.setUpdatetime(new Date());
        return toAjax(byDayFlowStandardService.insertByDayFlowStandard(byDayFlowStandard));
    }

    /**
     * 修改一日流程标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:standard:edit')")
    @Log(title = "一日流程标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayFlowStandard byDayFlowStandard)
    {
        byDayFlowStandard.setUpdateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayFlowStandard.setUpdatetime(new Date());
        return toAjax(byDayFlowStandardService.updateByDayFlowStandard(byDayFlowStandard));
    }

    /**
     * 删除一日流程标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:standard:remove')")
    @Log(title = "一日流程标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sids}")
    public AjaxResult remove(@PathVariable Long[] sids)
    {
        return toAjax(byDayFlowStandardService.deleteByDayFlowStandardByIds(sids));
    }
}

package com.ruoyi.project.benyi.controller;

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
import com.ruoyi.project.benyi.domain.ByDayFlowDetail;
import com.ruoyi.project.benyi.service.IByDayFlowDetailService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 一日流程Controller
 * 
 * @author tsbz
 * @date 2020-05-07
 */
@RestController
@RequestMapping("/benyi/dayflow/dayflowmanger")
public class ByDayFlowDetailController extends BaseController
{
    @Autowired
    private IByDayFlowDetailService byDayFlowDetailService;

    /**
     * 查询一日流程列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowmanger:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayFlowDetail byDayFlowDetail)
    {
        startPage();
        List<ByDayFlowDetail> list = byDayFlowDetailService.selectByDayFlowDetailList(byDayFlowDetail);
        //System.out.println("-------------------------------"+list);
        return getDataTable(list);
    }

    /**
     * 导出一日流程列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowmanger:export')")
    @Log(title = "一日流程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayFlowDetail byDayFlowDetail)
    {
        List<ByDayFlowDetail> list = byDayFlowDetailService.selectByDayFlowDetailList(byDayFlowDetail);
        ExcelUtil<ByDayFlowDetail> util = new ExcelUtil<ByDayFlowDetail>(ByDayFlowDetail.class);
        return util.exportExcel(list, "detail");
    }

    /**
     * 获取一日流程详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowmanger:query')"+ "||@ss.hasPermi('benyi:dayflowmanger:list')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byDayFlowDetailService.selectByDayFlowDetailById(id));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(ByDayFlowDetail byDayFlowDetail) {
        List<ByDayFlowDetail> byDayFlowDetails = byDayFlowDetailService.selectByDayFlowDetailListTree(byDayFlowDetail);
        return AjaxResult.success(byDayFlowDetailService.buildDayFlowDetailTreeSelect(byDayFlowDetails));
    }

    /**
     * 新增一日流程
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowmanger:add')")
    @Log(title = "一日流程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayFlowDetail byDayFlowDetail)
    {
        byDayFlowDetail.setCreateUser(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(byDayFlowDetailService.insertByDayFlowDetail(byDayFlowDetail));
    }

    /**
     * 修改一日流程
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowmanger:edit')")
    @Log(title = "一日流程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayFlowDetail byDayFlowDetail)
    {
        byDayFlowDetail.setUpdateUser(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(byDayFlowDetailService.updateByDayFlowDetail(byDayFlowDetail));
    }

    /**
     * 删除一日流程
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowmanger:remove')")
    @Log(title = "一日流程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byDayFlowDetailService.deleteByDayFlowDetailByIds(ids));
    }
}

package com.ruoyi.project.benyi.controller;

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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByChildCheckinDetail;
import com.ruoyi.project.benyi.service.IByChildCheckinDetailService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿考勤Controller
 *
 * @author tsbz
 * @date 2020-07-21
 */
@RestController
@RequestMapping("/benyi/checkindetail")
public class ByChildCheckinDetailController extends BaseController
{
    @Autowired
    private IByChildCheckinDetailService byChildCheckinDetailService;

/**
 * 查询幼儿考勤列表
 */
@PreAuthorize("@ss.hasPermi('benyi:checkindetail:list')")
@GetMapping("/list")
        public TableDataInfo list(ByChildCheckinDetail byChildCheckinDetail)
    {
        startPage();
        List<ByChildCheckinDetail> list = byChildCheckinDetailService.selectByChildCheckinDetailList(byChildCheckinDetail);
        return getDataTable(list);
    }
    
    /**
     * 导出幼儿考勤列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:checkindetail:export')")
    @Log(title = "幼儿考勤", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByChildCheckinDetail byChildCheckinDetail)
    {
        List<ByChildCheckinDetail> list = byChildCheckinDetailService.selectByChildCheckinDetailList(byChildCheckinDetail);
        ExcelUtil<ByChildCheckinDetail> util = new ExcelUtil<ByChildCheckinDetail>(ByChildCheckinDetail.class);
        return util.exportExcel(list, "detail");
    }

    /**
     * 获取幼儿考勤详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:checkindetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byChildCheckinDetailService.selectByChildCheckinDetailById(id));
    }

    /**
     * 新增幼儿考勤
     */
    @PreAuthorize("@ss.hasPermi('benyi:checkindetail:add')")
    @Log(title = "幼儿考勤", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByChildCheckinDetail byChildCheckinDetail)
    {
        return toAjax(byChildCheckinDetailService.insertByChildCheckinDetail(byChildCheckinDetail));
    }

    /**
     * 修改幼儿考勤
     */
    @PreAuthorize("@ss.hasPermi('benyi:checkindetail:edit')")
    @Log(title = "幼儿考勤", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByChildCheckinDetail byChildCheckinDetail)
    {
        return toAjax(byChildCheckinDetailService.updateByChildCheckinDetail(byChildCheckinDetail));
    }

    /**
     * 删除幼儿考勤
     */
    @PreAuthorize("@ss.hasPermi('benyi:checkindetail:remove')")
    @Log(title = "幼儿考勤", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byChildCheckinDetailService.deleteByChildCheckinDetailByIds(ids));
    }
}

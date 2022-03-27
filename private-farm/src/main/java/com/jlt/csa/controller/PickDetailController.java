package com.jlt.csa.controller;

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
import com.jlt.csa.domain.PickDetail;
import com.jlt.csa.service.IPickDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采摘任务明细Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/detail")
public class PickDetailController extends BaseController
{
    @Autowired
    private IPickDetailService pickDetailService;

    /**
     * 查询采摘任务明细列表
     */
    @PreAuthorize("@ss.hasPermi('csa:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(PickDetail pickDetail)
    {
        startPage();
        List<PickDetail> list = pickDetailService.selectPickDetailList(pickDetail);
        return getDataTable(list);
    }

    /**
     * 导出采摘任务明细列表
     */
    @PreAuthorize("@ss.hasPermi('csa:detail:export')")
    @Log(title = "采摘任务明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PickDetail pickDetail)
    {
        List<PickDetail> list = pickDetailService.selectPickDetailList(pickDetail);
        ExcelUtil<PickDetail> util = new ExcelUtil<PickDetail>(PickDetail.class);
        util.exportExcel(response, list, "采摘任务明细数据");
    }

    /**
     * 获取采摘任务明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:detail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(pickDetailService.selectPickDetailById(id));
    }

    /**
     * 新增采摘任务明细
     */
    @PreAuthorize("@ss.hasPermi('csa:detail:add')")
    @Log(title = "采摘任务明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PickDetail pickDetail)
    {
        return toAjax(pickDetailService.insertPickDetail(pickDetail));
    }

    /**
     * 修改采摘任务明细
     */
    @PreAuthorize("@ss.hasPermi('csa:detail:edit')")
    @Log(title = "采摘任务明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PickDetail pickDetail)
    {
        return toAjax(pickDetailService.updatePickDetail(pickDetail));
    }

    /**
     * 删除采摘任务明细
     */
    @PreAuthorize("@ss.hasPermi('csa:detail:remove')")
    @Log(title = "采摘任务明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pickDetailService.deletePickDetailByIds(ids));
    }
}

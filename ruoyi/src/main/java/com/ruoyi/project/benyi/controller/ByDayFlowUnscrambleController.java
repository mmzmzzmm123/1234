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
import com.ruoyi.project.benyi.domain.ByDayFlowUnscramble;
import com.ruoyi.project.benyi.service.IByDayFlowUnscrambleService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 一日流程解读Controller
 *
 * @author tsbz
 * @date 2020-06-04
 */
@RestController
@RequestMapping("/benyi/unscramble")
public class ByDayFlowUnscrambleController extends BaseController {
    @Autowired
    private IByDayFlowUnscrambleService byDayFlowUnscrambleService;

    /**
     * 查询一日流程解读列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:unscramble:list')" + "||@ss.hasPermi('benyi:dayflowmanger:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayFlowUnscramble byDayFlowUnscramble) {
        startPage();
        List<ByDayFlowUnscramble> list = byDayFlowUnscrambleService.selectByDayFlowUnscrambleList(byDayFlowUnscramble);
        return getDataTable(list);
    }

    /**
     * 导出一日流程解读列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:unscramble:export')")
    @Log(title = "一日流程解读", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayFlowUnscramble byDayFlowUnscramble) {
        List<ByDayFlowUnscramble> list = byDayFlowUnscrambleService.selectByDayFlowUnscrambleList(byDayFlowUnscramble);
        ExcelUtil<ByDayFlowUnscramble> util = new ExcelUtil<ByDayFlowUnscramble>(ByDayFlowUnscramble.class);
        return util.exportExcel(list, "unscramble");
    }

    /**
     * 获取一日流程解读详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:unscramble:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byDayFlowUnscrambleService.selectByDayFlowUnscrambleById(id));
    }

    /**
     * 新增一日流程解读
     */
    @PreAuthorize("@ss.hasPermi('benyi:unscramble:add')")
    @Log(title = "一日流程解读", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayFlowUnscramble byDayFlowUnscramble) {
        return toAjax(byDayFlowUnscrambleService.insertByDayFlowUnscramble(byDayFlowUnscramble));
    }

    /**
     * 修改一日流程解读
     */
    @PreAuthorize("@ss.hasPermi('benyi:unscramble:edit')")
    @Log(title = "一日流程解读", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayFlowUnscramble byDayFlowUnscramble) {
        return toAjax(byDayFlowUnscrambleService.updateByDayFlowUnscramble(byDayFlowUnscramble));
    }

    /**
     * 删除一日流程解读
     */
    @PreAuthorize("@ss.hasPermi('benyi:unscramble:remove')")
    @Log(title = "一日流程解读", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byDayFlowUnscrambleService.deleteByDayFlowUnscrambleByIds(ids));
    }
}

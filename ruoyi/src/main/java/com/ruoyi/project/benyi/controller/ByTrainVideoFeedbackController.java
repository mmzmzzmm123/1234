package com.ruoyi.project.benyi.service.impl;

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
import com.ruoyi.project.benyi.domain.ByTrainVideoFeedback;
import com.ruoyi.project.benyi.service.IByTrainVideoFeedbackService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 培训视频评价反馈Controller
 * 
 * @author tsbz
 * @date 2020-06-01
 */
@RestController
@RequestMapping("/benyi/feedback")
public class ByTrainVideoFeedbackController extends BaseController
{
    @Autowired
    private IByTrainVideoFeedbackService byTrainVideoFeedbackService;

    /**
     * 查询培训视频评价反馈列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByTrainVideoFeedback byTrainVideoFeedback)
    {
        startPage();
        List<ByTrainVideoFeedback> list = byTrainVideoFeedbackService.selectByTrainVideoFeedbackList(byTrainVideoFeedback);
        return getDataTable(list);
    }

    /**
     * 导出培训视频评价反馈列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:feedback:export')")
    @Log(title = "培训视频评价反馈", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByTrainVideoFeedback byTrainVideoFeedback)
    {
        List<ByTrainVideoFeedback> list = byTrainVideoFeedbackService.selectByTrainVideoFeedbackList(byTrainVideoFeedback);
        ExcelUtil<ByTrainVideoFeedback> util = new ExcelUtil<ByTrainVideoFeedback>(ByTrainVideoFeedback.class);
        return util.exportExcel(list, "feedback");
    }

    /**
     * 获取培训视频评价反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:feedback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byTrainVideoFeedbackService.selectByTrainVideoFeedbackById(id));
    }

    /**
     * 新增培训视频评价反馈
     */
    @PreAuthorize("@ss.hasPermi('benyi:feedback:add')")
    @Log(title = "培训视频评价反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByTrainVideoFeedback byTrainVideoFeedback)
    {
        return toAjax(byTrainVideoFeedbackService.insertByTrainVideoFeedback(byTrainVideoFeedback));
    }

    /**
     * 修改培训视频评价反馈
     */
    @PreAuthorize("@ss.hasPermi('benyi:feedback:edit')")
    @Log(title = "培训视频评价反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByTrainVideoFeedback byTrainVideoFeedback)
    {
        return toAjax(byTrainVideoFeedbackService.updateByTrainVideoFeedback(byTrainVideoFeedback));
    }

    /**
     * 删除培训视频评价反馈
     */
    @PreAuthorize("@ss.hasPermi('benyi:feedback:remove')")
    @Log(title = "培训视频评价反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byTrainVideoFeedbackService.deleteByTrainVideoFeedbackByIds(ids));
    }
}

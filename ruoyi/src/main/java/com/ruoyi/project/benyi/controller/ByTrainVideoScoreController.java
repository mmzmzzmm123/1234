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
import com.ruoyi.project.benyi.domain.ByTrainVideoScore;
import com.ruoyi.project.benyi.service.IByTrainVideoScoreService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 培训视频评分Controller
 * 
 * @author tsbz
 * @date 2020-06-01
 */
@RestController
@RequestMapping("/benyi/score")
public class ByTrainVideoScoreController extends BaseController
{
    @Autowired
    private IByTrainVideoScoreService byTrainVideoScoreService;

    /**
     * 查询培训视频评分列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByTrainVideoScore byTrainVideoScore)
    {
        startPage();
        List<ByTrainVideoScore> list = byTrainVideoScoreService.selectByTrainVideoScoreList(byTrainVideoScore);
        return getDataTable(list);
    }

    /**
     * 导出培训视频评分列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:score:export')")
    @Log(title = "培训视频评分", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByTrainVideoScore byTrainVideoScore)
    {
        List<ByTrainVideoScore> list = byTrainVideoScoreService.selectByTrainVideoScoreList(byTrainVideoScore);
        ExcelUtil<ByTrainVideoScore> util = new ExcelUtil<ByTrainVideoScore>(ByTrainVideoScore.class);
        return util.exportExcel(list, "score");
    }

    /**
     * 获取培训视频评分详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:score:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byTrainVideoScoreService.selectByTrainVideoScoreById(id));
    }

    /**
     * 新增培训视频评分
     */
    @PreAuthorize("@ss.hasPermi('benyi:score:add')")
    @Log(title = "培训视频评分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByTrainVideoScore byTrainVideoScore)
    {
        return toAjax(byTrainVideoScoreService.insertByTrainVideoScore(byTrainVideoScore));
    }

    /**
     * 修改培训视频评分
     */
    @PreAuthorize("@ss.hasPermi('benyi:score:edit')")
    @Log(title = "培训视频评分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByTrainVideoScore byTrainVideoScore)
    {
        return toAjax(byTrainVideoScoreService.updateByTrainVideoScore(byTrainVideoScore));
    }

    /**
     * 删除培训视频评分
     */
    @PreAuthorize("@ss.hasPermi('benyi:score:remove')")
    @Log(title = "培训视频评分", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byTrainVideoScoreService.deleteByTrainVideoScoreByIds(ids));
    }
}

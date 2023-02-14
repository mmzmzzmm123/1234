package com.ruoyi.web.controller.contest;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.contest.domain.SubContest;
import com.ruoyi.contest.service.ISubContestService;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 子竞赛信息Controller
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contest/subContest")
public class SubContestController extends BaseController
{
    private final ISubContestService subContestService;

    /**
     * 查询子竞赛信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:subContest:list')")
    @GetMapping("/list")
    public TableDataInfo list(SubContest subContest)
    {
        startPage();
        List<SubContest> list = subContestService.selectSubContestList(subContest);
        return getDataTable(list);
    }

    /**
     * 导出子竞赛信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:subContest:export')")
    @Log(title = "子竞赛信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SubContest subContest)
    {
        List<SubContest> list = subContestService.selectSubContestList(subContest);
        ExcelUtil<SubContest> util = new ExcelUtil<SubContest>(SubContest.class);
        util.exportExcel(response, list, "子竞赛信息数据");
    }

    /**
     * 获取子竞赛信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('contest:subContest:query')")
    @GetMapping(value = "/{subContestId}")
    public AjaxResult getInfo(@PathVariable("subContestId") Long subContestId)
    {
        return success(subContestService.getById(subContestId));
    }

    /**
     * 新增子竞赛信息
     */
    @PreAuthorize("@ss.hasPermi('contest:subContest:add')")
    @Log(title = "子竞赛信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SubContest subContest)
    {
        return toAjax(subContestService.save(subContest));
    }

    /**
     * 修改子竞赛信息
     */
    @PreAuthorize("@ss.hasPermi('contest:subContest:edit')")
    @Log(title = "子竞赛信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SubContest subContest)
    {
        return toAjax(subContestService.updateById(subContest));
    }

    /**
     * 删除子竞赛信息
     */
    @PreAuthorize("@ss.hasPermi('contest:subContest:remove')")
    @Log(title = "子竞赛信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{subContestIds}")
    public AjaxResult remove(@PathVariable Long[] subContestIds)
    {
        return toAjax(subContestService.removeByIds(Arrays.asList(subContestIds)));
    }
}

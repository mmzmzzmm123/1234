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

import com.ruoyi.contest.domain.ContestInfo;
import com.ruoyi.contest.service.IContestInfoService;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 竞赛基础信息Controller
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contest/contestInfo")
public class ContestInfoController extends BaseController
{
    private final IContestInfoService contestInfoService;

    /**
     * 查询竞赛基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:contestInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContestInfo contestInfo)
    {
        startPage();
        List<ContestInfo> list = contestInfoService.selectContestInfoList(contestInfo);
        return getDataTable(list);
    }

    /**
     * 导出竞赛基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:contestInfo:export')")
    @Log(title = "竞赛基础信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContestInfo contestInfo)
    {
        List<ContestInfo> list = contestInfoService.selectContestInfoList(contestInfo);
        ExcelUtil<ContestInfo> util = new ExcelUtil<ContestInfo>(ContestInfo.class);
        util.exportExcel(response, list, "竞赛基础信息数据");
    }

    /**
     * 获取竞赛基础信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('contest:contestInfo:query')")
    @GetMapping(value = "/{contestId}")
    public AjaxResult getInfo(@PathVariable("contestId") Long contestId)
    {
        return success(contestInfoService.getById(contestId));
    }

    /**
     * 新增竞赛基础信息
     */
    @PreAuthorize("@ss.hasPermi('contest:contestInfo:add')")
    @Log(title = "竞赛基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContestInfo contestInfo)
    {
        return toAjax(contestInfoService.save(contestInfo));
    }

    /**
     * 修改竞赛基础信息
     */
    @PreAuthorize("@ss.hasPermi('contest:contestInfo:edit')")
    @Log(title = "竞赛基础信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContestInfo contestInfo)
    {
        return toAjax(contestInfoService.updateById(contestInfo));
    }

    /**
     * 删除竞赛基础信息
     */
    @PreAuthorize("@ss.hasPermi('contest:contestInfo:remove')")
    @Log(title = "竞赛基础信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contestIds}")
    public AjaxResult remove(@PathVariable Long[] contestIds)
    {
        return toAjax(contestInfoService.removeByIds(Arrays.asList(contestIds)));
    }
}

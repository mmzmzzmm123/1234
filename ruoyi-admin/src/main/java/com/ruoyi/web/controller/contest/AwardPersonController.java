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

import com.ruoyi.contest.domain.AwardPerson;
import com.ruoyi.contest.service.IAwardPersonService;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 参赛人员信息Controller
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contest/awardPerson")
public class AwardPersonController extends BaseController
{
    private final IAwardPersonService awardPersonService;

    /**
     * 查询参赛人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:awardPerson:list')")
    @GetMapping("/list")
    public TableDataInfo list(AwardPerson awardPerson)
    {
        startPage();
        List<AwardPerson> list = awardPersonService.selectAwardPersonList(awardPerson);
        return getDataTable(list);
    }

    /**
     * 导出参赛人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('contest:awardPerson:export')")
    @Log(title = "参赛人员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AwardPerson awardPerson)
    {
        List<AwardPerson> list = awardPersonService.selectAwardPersonList(awardPerson);
        ExcelUtil<AwardPerson> util = new ExcelUtil<AwardPerson>(AwardPerson.class);
        util.exportExcel(response, list, "参赛人员信息数据");
    }

    /**
     * 获取参赛人员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('contest:awardPerson:query')")
    @GetMapping(value = "/{awardPersonId}")
    public AjaxResult getInfo(@PathVariable("awardPersonId") Long awardPersonId)
    {
        return success(awardPersonService.getById(awardPersonId));
    }

    /**
     * 新增参赛人员信息
     */
    @PreAuthorize("@ss.hasPermi('contest:awardPerson:add')")
    @Log(title = "参赛人员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AwardPerson awardPerson)
    {
        return toAjax(awardPersonService.save(awardPerson));
    }

    /**
     * 修改参赛人员信息
     */
    @PreAuthorize("@ss.hasPermi('contest:awardPerson:edit')")
    @Log(title = "参赛人员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AwardPerson awardPerson)
    {
        return toAjax(awardPersonService.updateById(awardPerson));
    }

    /**
     * 删除参赛人员信息
     */
    @PreAuthorize("@ss.hasPermi('contest:awardPerson:remove')")
    @Log(title = "参赛人员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{awardPersonIds}")
    public AjaxResult remove(@PathVariable Long[] awardPersonIds)
    {
        return toAjax(awardPersonService.removeByIds(Arrays.asList(awardPersonIds)));
    }
}

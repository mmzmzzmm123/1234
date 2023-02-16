package com.ruoyi.web.controller.contest;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.contest.domain.Award;
import com.ruoyi.contest.domain.vo.SaveAwardVo;
import com.ruoyi.contest.service.IAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 获奖登记Controller
 *
 * @author lsyonlygoddes
 * @date 2023-02-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contest/award")
public class AwardController extends BaseController
{
    private final IAwardService awardService;

    /**
     * 查询获奖登记列表
     */
    @PreAuthorize("@ss.hasPermi('contest:award:list')")
    @GetMapping("/list")
    public TableDataInfo list(Award award)
    {
        startPage();
        List<Award> list = awardService.selectAwardList(award);
        return getDataTable(list);
    }

    /**
     * 导出获奖登记列表
     */
    @PreAuthorize("@ss.hasPermi('contest:award:export')")
    @Log(title = "获奖登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Award award)
    {
        List<Award> list = awardService.selectAwardList(award);
        ExcelUtil<Award> util = new ExcelUtil<Award>(Award.class);
        util.exportExcel(response, list, "获奖登记数据");
    }

    /**
     * 获取获奖登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('contest:award:query')")
    @GetMapping(value = "/{awardId}")
    public AjaxResult getInfo(@PathVariable("awardId") Long awardId)
    {
        return success(awardService.getById(awardId));
    }

    /**
     * 新增获奖登记
     */
    @PreAuthorize("@ss.hasPermi('contest:award:add')")
    @Log(title = "获奖登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Award award)
    {
        return toAjax(awardService.save(award));
    }

    /**
     * 新增获奖登记by SaveAwardVo
     */
    @PreAuthorize("@ss.hasPermi('contest:award:add')")
    @Log(title = "获奖登记", businessType = BusinessType.INSERT)
    @PostMapping("/saveAwardVo")
    public AjaxResult add(@RequestBody SaveAwardVo vo)
    {
        return toAjax(awardService.saveAward(vo));
    }

    /**
     * 修改获奖登记
     */
    @PreAuthorize("@ss.hasPermi('contest:award:edit')")
    @Log(title = "获奖登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Award award)
    {
        return toAjax(awardService.updateById(award));
    }

    /**
     * 删除获奖登记
     */
    @PreAuthorize("@ss.hasPermi('contest:award:remove')")
    @Log(title = "获奖登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{awardIds}")
    public AjaxResult remove(@PathVariable Long[] awardIds)
    {
        return toAjax(awardService.removeByIds(Arrays.asList(awardIds)));
    }
}

package com.ruoyi.web.controller.onethinker;

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

import com.ruoyi.onethinker.domain.PlatformUserIntegralHistory;
import com.ruoyi.onethinker.service.IPlatformUserIntegralHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 平台用户积分流水记录Controller
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@RestController
@RequestMapping("/onethinker/history")
public class PlatformUserIntegralHistoryController extends BaseController {
    @Autowired
    private IPlatformUserIntegralHistoryService platformUserIntegralHistoryService;

/**
 * 查询平台用户积分流水记录列表
 */
@PreAuthorize("@ss.hasPermi('onethinker:history:list')")
@GetMapping("/list")
    public TableDataInfo list(PlatformUserIntegralHistory platformUserIntegralHistory) {
        startPage();
        List<PlatformUserIntegralHistory> list = platformUserIntegralHistoryService.selectPlatformUserIntegralHistoryList(platformUserIntegralHistory);
        return getDataTable(list);
    }

    /**
     * 导出平台用户积分流水记录列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:history:export')")
    @Log(title = "平台用户积分流水记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformUserIntegralHistory platformUserIntegralHistory) {
        List<PlatformUserIntegralHistory> list = platformUserIntegralHistoryService.selectPlatformUserIntegralHistoryList(platformUserIntegralHistory);
        ExcelUtil<PlatformUserIntegralHistory> util = new ExcelUtil<PlatformUserIntegralHistory>(PlatformUserIntegralHistory. class);
        util.exportExcel(response, list, "平台用户积分流水记录数据");
    }

    /**
     * 获取平台用户积分流水记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:history:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(platformUserIntegralHistoryService.selectPlatformUserIntegralHistoryById(id));
    }

    /**
     * 新增平台用户积分流水记录
     */
    @PreAuthorize("@ss.hasPermi('onethinker:history:add')")
    @Log(title = "平台用户积分流水记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformUserIntegralHistory platformUserIntegralHistory) {
        return toAjax(platformUserIntegralHistoryService.insertPlatformUserIntegralHistory(platformUserIntegralHistory));
    }

    /**
     * 修改平台用户积分流水记录
     */
    @PreAuthorize("@ss.hasPermi('onethinker:history:edit')")
    @Log(title = "平台用户积分流水记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformUserIntegralHistory platformUserIntegralHistory) {
        return toAjax(platformUserIntegralHistoryService.updatePlatformUserIntegralHistory(platformUserIntegralHistory));
    }

    /**
     * 删除平台用户积分流水记录
     */
    @PreAuthorize("@ss.hasPermi('onethinker:history:remove')")
    @Log(title = "平台用户积分流水记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(platformUserIntegralHistoryService.deletePlatformUserIntegralHistoryByIds(ids));
    }
}

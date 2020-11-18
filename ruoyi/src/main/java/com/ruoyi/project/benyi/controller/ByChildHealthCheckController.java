package com.ruoyi.project.benyi.controller;

import java.util.Date;
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
import com.ruoyi.project.benyi.domain.ByChildHealthCheck;
import com.ruoyi.project.benyi.service.IByChildHealthCheckService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 儿童常规体检记录Controller
 *
 * @author tsbz
 * @date 2020-08-07
 */
@RestController
@RequestMapping("/benyi/healthcheck")
public class ByChildHealthCheckController extends BaseController {
    @Autowired
    private IByChildHealthCheckService byChildHealthCheckService;

    /**
     * 查询儿童常规体检记录列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:healthcheck:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByChildHealthCheck byChildHealthCheck) {
        startPage();
        List<ByChildHealthCheck> list = byChildHealthCheckService.selectByChildHealthCheckList(byChildHealthCheck);
        return getDataTable(list);
    }

    /**
     * 导出儿童常规体检记录列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:healthcheck:export')")
    @Log(title = "儿童常规体检记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByChildHealthCheck byChildHealthCheck) {
        List<ByChildHealthCheck> list = byChildHealthCheckService.selectByChildHealthCheckList(byChildHealthCheck);
        ExcelUtil<ByChildHealthCheck> util = new ExcelUtil<ByChildHealthCheck>(ByChildHealthCheck.class);
        return util.exportExcel(list, "healthcheck");
    }

    /**
     * 获取儿童常规体检记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:healthcheck:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byChildHealthCheckService.selectByChildHealthCheckById(id));
    }

    /**
     * 新增儿童常规体检记录
     */
    @PreAuthorize("@ss.hasPermi('benyi:healthcheck:add')")
    @Log(title = "儿童常规体检记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByChildHealthCheck byChildHealthCheck) {
        byChildHealthCheck.setCreatetime(new Date());
        return toAjax(byChildHealthCheckService.insertByChildHealthCheck(byChildHealthCheck));
    }

    /**
     * 修改儿童常规体检记录
     */
    @PreAuthorize("@ss.hasPermi('benyi:healthcheck:edit')")
    @Log(title = "儿童常规体检记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByChildHealthCheck byChildHealthCheck) {

        return toAjax(byChildHealthCheckService.updateByChildHealthCheck(byChildHealthCheck));
    }

    /**
     * 删除儿童常规体检记录
     */
    @PreAuthorize("@ss.hasPermi('benyi:healthcheck:remove')")
    @Log(title = "儿童常规体检记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byChildHealthCheckService.deleteByChildHealthCheckByIds(ids));
    }
}

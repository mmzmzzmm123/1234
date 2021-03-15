package com.ruoyi.web.controller.jyykhgl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.jyykhgl.domain.TsbzJyykhrw;
import com.ruoyi.jyykhgl.service.ITsbzJyykhrwService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教研员考核任务Controller
 *
 * @author tsbz
 * @date 2021-01-15
 */
@RestController
@RequestMapping("/jyykhgl/jyykhrw")
public class TsbzJyykhrwController extends BaseController {
    @Autowired
    private ITsbzJyykhrwService tsbzJyykhrwService;

    /**
     * 查询教研员考核任务列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhrw:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyykhrw tsbzJyykhrw) {
        startPage();
        List<TsbzJyykhrw> list = tsbzJyykhrwService.selectTsbzJyykhrwList(tsbzJyykhrw);
        return getDataTable(list);
    }

    /**
     * 查询教研员考核任务列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhrw:list')")
    @GetMapping("/statistics")
    public TableDataInfo statistics(TsbzJyykhrw tsbzJyykhrw) {
        startPage();
        List<TsbzJyykhrw> list = tsbzJyykhrwService.selectTsbzJyykhrwStatistics(tsbzJyykhrw);
        return getDataTable(list);
    }

    /**
     * 导出教研员考核任务列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhrw:export')")
    @Log(title = "教研员考核任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyykhrw tsbzJyykhrw) {
        List<TsbzJyykhrw> list = tsbzJyykhrwService.selectTsbzJyykhrwList(tsbzJyykhrw);
        ExcelUtil<TsbzJyykhrw> util = new ExcelUtil<TsbzJyykhrw>(TsbzJyykhrw.class);
        return util.exportExcel(list, "jyykhrw");
    }

    /**
     * 获取教研员考核任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhrw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyykhrwService.selectTsbzJyykhrwById(id));
    }

    /**
     * 新增教研员考核任务
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhrw:add')")
    @Log(title = "教研员考核任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyykhrw tsbzJyykhrw) {
        tsbzJyykhrw.setKhbm(SecurityUtils.getLoginUser().getUser().getDeptId().toString());
        tsbzJyykhrw.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(tsbzJyykhrwService.insertTsbzJyykhrw(tsbzJyykhrw));
    }

    /**
     * 修改教研员考核任务
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhrw:edit')")
    @Log(title = "教研员考核任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyykhrw tsbzJyykhrw) {
        return toAjax(tsbzJyykhrwService.updateTsbzJyykhrw(tsbzJyykhrw));
    }

    /**
     * 删除教研员考核任务
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhrw:remove')")
    @Log(title = "教研员考核任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyykhrwService.deleteTsbzJyykhrwByIds(ids));
    }
}

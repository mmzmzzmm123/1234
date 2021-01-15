package com.ruoyi.web.controller.jyykhgl;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.jyykhgl.domain.TsbzJyydyzd;
import com.ruoyi.jyykhgl.service.ITsbzJyydyzdService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 调研指导Controller
 *
 * @author tsbz
 * @date 2021-01-15
 */
@RestController
@RequestMapping("/jyykhgl/jyydyzd")
public class TsbzJyydyzdController extends BaseController {
    @Autowired
    private ITsbzJyydyzdService tsbzJyydyzdService;

    /**
     * 查询调研指导列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydyzd:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyydyzd tsbzJyydyzd) {
        startPage();
        List<TsbzJyydyzd> list = tsbzJyydyzdService.selectTsbzJyydyzdList(tsbzJyydyzd);
        return getDataTable(list);
    }

    /**
     * 导出调研指导列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydyzd:export')")
    @Log(title = "调研指导", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyydyzd tsbzJyydyzd) {
        List<TsbzJyydyzd> list = tsbzJyydyzdService.selectTsbzJyydyzdList(tsbzJyydyzd);
        ExcelUtil<TsbzJyydyzd> util = new ExcelUtil<TsbzJyydyzd>(TsbzJyydyzd.class);
        return util.exportExcel(list, "jyydyzd");
    }

    /**
     * 获取调研指导详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydyzd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyydyzdService.selectTsbzJyydyzdById(id));
    }

    /**
     * 新增调研指导
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydyzd:add')")
    @Log(title = "调研指导", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyydyzd tsbzJyydyzd) {
        return toAjax(tsbzJyydyzdService.insertTsbzJyydyzd(tsbzJyydyzd));
    }

    /**
     * 修改调研指导
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydyzd:edit')")
    @Log(title = "调研指导", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyydyzd tsbzJyydyzd) {
        return toAjax(tsbzJyydyzdService.updateTsbzJyydyzd(tsbzJyydyzd));
    }

    /**
     * 删除调研指导
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydyzd:remove')")
    @Log(title = "调研指导", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyydyzdService.deleteTsbzJyydyzdByIds(ids));
    }
}

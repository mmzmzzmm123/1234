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
import com.ruoyi.jyykhgl.domain.TsbzJyykhpjjg;
import com.ruoyi.jyykhgl.service.ITsbzJyykhpjjgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核评价结果Controller
 *
 * @author tsbz
 * @date 2021-04-16
 */
@RestController
@RequestMapping("/jyykhgl/jyykhpjjg")
public class TsbzJyykhpjjgController extends BaseController {
    @Autowired
    private ITsbzJyykhpjjgService tsbzJyykhpjjgService;

    /**
     * 查询考核评价结果列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhpjjg:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyykhpjjg tsbzJyykhpjjg) {
        startPage();
        List<TsbzJyykhpjjg> list = tsbzJyykhpjjgService.selectTsbzJyykhpjjgList(tsbzJyykhpjjg);
        return getDataTable(list);
    }

    /**
     * 导出考核评价结果列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhpjjg:export')")
    @Log(title = "考核评价结果", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyykhpjjg tsbzJyykhpjjg) {
        List<TsbzJyykhpjjg> list = tsbzJyykhpjjgService.selectTsbzJyykhpjjgList(tsbzJyykhpjjg);
        ExcelUtil<TsbzJyykhpjjg> util = new ExcelUtil<TsbzJyykhpjjg>(TsbzJyykhpjjg.class);
        return util.exportExcel(list, "jyykhpjjg");
    }

    /**
     * 获取考核评价结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhpjjg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyykhpjjgService.selectTsbzJyykhpjjgById(id));
    }

    /**
     * 新增考核评价结果
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhpjjg:add')")
    @Log(title = "考核评价结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyykhpjjg tsbzJyykhpjjg) {
        return toAjax(tsbzJyykhpjjgService.insertTsbzJyykhpjjg(tsbzJyykhpjjg));
    }

    /**
     * 修改考核评价结果
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhpjjg:edit')")
    @Log(title = "考核评价结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyykhpjjg tsbzJyykhpjjg) {
        return toAjax(tsbzJyykhpjjgService.updateTsbzJyykhpjjg(tsbzJyykhpjjg));
    }

    /**
     * 删除考核评价结果
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyykhpjjg:remove')")
    @Log(title = "考核评价结果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyykhpjjgService.deleteTsbzJyykhpjjgByIds(ids));
    }
}

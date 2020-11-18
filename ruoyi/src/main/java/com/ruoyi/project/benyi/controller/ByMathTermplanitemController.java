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
import com.ruoyi.project.benyi.domain.ByMathTermplanitem;
import com.ruoyi.project.benyi.service.IByMathTermplanitemService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 游戏数学学期计划明细Controller
 *
 * @author tsbz
 * @date 2020-10-29
 */
@RestController
@RequestMapping("/benyi/mathtermplanitem")
public class ByMathTermplanitemController extends BaseController {
    @Autowired
    private IByMathTermplanitemService byMathTermplanitemService;

    /**
     * 查询游戏数学学期计划明细列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByMathTermplanitem byMathTermplanitem) {
        startPage();
        List<ByMathTermplanitem> list = byMathTermplanitemService.selectByMathTermplanitemList(byMathTermplanitem);
        return getDataTable(list);
    }

    /**
     * 导出游戏数学学期计划明细列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:export')")
    @Log(title = "游戏数学学期计划明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByMathTermplanitem byMathTermplanitem) {
        List<ByMathTermplanitem> list = byMathTermplanitemService.selectByMathTermplanitemList(byMathTermplanitem);
        ExcelUtil<ByMathTermplanitem> util = new ExcelUtil<ByMathTermplanitem>(ByMathTermplanitem.class);
        return util.exportExcel(list, "mathtermplanitem");
    }

    /**
     * 获取游戏数学学期计划明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byMathTermplanitemService.selectByMathTermplanitemById(id));
    }

    /**
     * 新增游戏数学学期计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:add')")
    @Log(title = "游戏数学学期计划明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByMathTermplanitem byMathTermplanitem) {
        return toAjax(byMathTermplanitemService.insertByMathTermplanitem(byMathTermplanitem));
    }

    /**
     * 修改游戏数学学期计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:edit')")
    @Log(title = "游戏数学学期计划明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByMathTermplanitem byMathTermplanitem) {
        return toAjax(byMathTermplanitemService.updateByMathTermplanitem(byMathTermplanitem));
    }

    /**
     * 删除游戏数学学期计划明细
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:remove')")
    @Log(title = "游戏数学学期计划明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byMathTermplanitemService.deleteByMathTermplanitemByIds(ids));
    }
}

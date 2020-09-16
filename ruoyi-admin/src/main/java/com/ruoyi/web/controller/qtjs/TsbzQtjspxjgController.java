package com.ruoyi.web.controller.qtjs;

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
import com.ruoyi.qtjs.domain.TsbzQtjspxjg;
import com.ruoyi.qtjs.service.ITsbzQtjspxjgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师评选结果Controller
 *
 * @author ruoyi
 * @date 2020-09-16
 */
@RestController
@RequestMapping("/qtjs/qtjspxjg")
public class TsbzQtjspxjgController extends BaseController {
    @Autowired
    private ITsbzQtjspxjgService tsbzQtjspxjgService;

    /**
     * 查询群体教师评选结果列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxjg:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjspxjg tsbzQtjspxjg) {
        startPage();
        List<TsbzQtjspxjg> list = tsbzQtjspxjgService.selectTsbzQtjspxjgList(tsbzQtjspxjg);
        return getDataTable(list);
    }

    /**
     * 导出群体教师评选结果列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxjg:export')")
    @Log(title = "群体教师评选结果", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjspxjg tsbzQtjspxjg) {
        List<TsbzQtjspxjg> list = tsbzQtjspxjgService.selectTsbzQtjspxjgList(tsbzQtjspxjg);
        ExcelUtil<TsbzQtjspxjg> util = new ExcelUtil<TsbzQtjspxjg>(TsbzQtjspxjg.class);
        return util.exportExcel(list, "qtjspxjg");
    }

    /**
     * 获取群体教师评选结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxjg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzQtjspxjgService.selectTsbzQtjspxjgById(id));
    }

    /**
     * 新增群体教师评选结果
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxjg:add')")
    @Log(title = "群体教师评选结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjspxjg tsbzQtjspxjg) {
        return toAjax(tsbzQtjspxjgService.insertTsbzQtjspxjg(tsbzQtjspxjg));
    }

    /**
     * 修改群体教师评选结果
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxjg:edit')")
    @Log(title = "群体教师评选结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjspxjg tsbzQtjspxjg) {
        return toAjax(tsbzQtjspxjgService.updateTsbzQtjspxjg(tsbzQtjspxjg));
    }

    /**
     * 删除群体教师评选结果
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxjg:remove')")
    @Log(title = "群体教师评选结果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjspxjgService.deleteTsbzQtjspxjgByIds(ids));
    }
}

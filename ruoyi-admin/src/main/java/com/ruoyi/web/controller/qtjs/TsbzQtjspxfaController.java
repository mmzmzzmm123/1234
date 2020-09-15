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
import com.ruoyi.qtjs.domain.TsbzQtjspxfa;
import com.ruoyi.qtjs.service.ITsbzQtjspxfaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师评选方案Controller
 *
 * @author ruoyi
 * @date 2020-09-15
 */
@RestController
@RequestMapping("/qtjs/qtjspxfa")
public class TsbzQtjspxfaController extends BaseController {
    @Autowired
    private ITsbzQtjspxfaService tsbzQtjspxfaService;

    /**
     * 查询群体教师评选方案列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjspxfa tsbzQtjspxfa) {
        startPage();
        List<TsbzQtjspxfa> list = tsbzQtjspxfaService.selectTsbzQtjspxfaList(tsbzQtjspxfa);
        return getDataTable(list);
    }

    /**
     * 导出群体教师评选方案列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:export')")
    @Log(title = "群体教师评选方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjspxfa tsbzQtjspxfa) {
        List<TsbzQtjspxfa> list = tsbzQtjspxfaService.selectTsbzQtjspxfaList(tsbzQtjspxfa);
        ExcelUtil<TsbzQtjspxfa> util = new ExcelUtil<TsbzQtjspxfa>(TsbzQtjspxfa.class);
        return util.exportExcel(list, "qtjspxfa");
    }

    /**
     * 获取群体教师评选方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzQtjspxfaService.selectTsbzQtjspxfaById(id));
    }

    /**
     * 新增群体教师评选方案
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:add')")
    @Log(title = "群体教师评选方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjspxfa tsbzQtjspxfa) {
        return toAjax(tsbzQtjspxfaService.insertTsbzQtjspxfa(tsbzQtjspxfa));
    }

    /**
     * 修改群体教师评选方案
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:edit')")
    @Log(title = "群体教师评选方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjspxfa tsbzQtjspxfa) {
        return toAjax(tsbzQtjspxfaService.updateTsbzQtjspxfa(tsbzQtjspxfa));
    }

    /**
     * 删除群体教师评选方案
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:remove')")
    @Log(title = "群体教师评选方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjspxfaService.deleteTsbzQtjspxfaByIds(ids));
    }
}

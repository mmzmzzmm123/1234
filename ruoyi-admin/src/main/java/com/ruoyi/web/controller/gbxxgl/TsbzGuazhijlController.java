package com.ruoyi.web.controller.gbxxgl;

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
import com.ruoyi.gbxxgl.domain.TsbzGuazhijl;
import com.ruoyi.gbxxgl.service.ITsbzGuazhijlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部挂职经历Controller
 *
 * @author ruoyi
 * @date 2020-12-08
 */
@RestController
@RequestMapping("/gbxxgl/guazhijl")
public class TsbzGuazhijlController extends BaseController {
    @Autowired
    private ITsbzGuazhijlService tsbzGuazhijlService;

    /**
     * 查询干部挂职经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:guazhijl:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGuazhijl tsbzGuazhijl) {
        startPage();
        List<TsbzGuazhijl> list = tsbzGuazhijlService.selectTsbzGuazhijlList(tsbzGuazhijl);
        return getDataTable(list);
    }

    /**
     * 导出干部挂职经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:guazhijl:export')")
    @Log(title = "干部挂职经历", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGuazhijl tsbzGuazhijl) {
        List<TsbzGuazhijl> list = tsbzGuazhijlService.selectTsbzGuazhijlList(tsbzGuazhijl);
        ExcelUtil<TsbzGuazhijl> util = new ExcelUtil<TsbzGuazhijl>(TsbzGuazhijl.class);
        return util.exportExcel(list, "guazhijl");
    }

    /**
     * 获取干部挂职经历详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:guazhijl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGuazhijlService.selectTsbzGuazhijlById(id));
    }

    /**
     * 新增干部挂职经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:guazhijl:add')")
    @Log(title = "干部挂职经历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGuazhijl tsbzGuazhijl) {
        return toAjax(tsbzGuazhijlService.insertTsbzGuazhijl(tsbzGuazhijl));
    }

    /**
     * 修改干部挂职经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:guazhijl:edit')")
    @Log(title = "干部挂职经历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGuazhijl tsbzGuazhijl) {
        return toAjax(tsbzGuazhijlService.updateTsbzGuazhijl(tsbzGuazhijl));
    }

    /**
     * 删除干部挂职经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:guazhijl:remove')")
    @Log(title = "干部挂职经历", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGuazhijlService.deleteTsbzGuazhijlByIds(ids));
    }
}

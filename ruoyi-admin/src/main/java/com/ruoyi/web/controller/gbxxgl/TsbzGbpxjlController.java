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
import com.ruoyi.gbxxgl.domain.TsbzGbpxjl;
import com.ruoyi.gbxxgl.service.ITsbzGbpxjlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部培训经历Controller
 *
 * @author ruoyi
 * @date 2020-12-08
 */
@RestController
@RequestMapping("/gbxxgl/gbpxjl")
public class TsbzGbpxjlController extends BaseController {
    @Autowired
    private ITsbzGbpxjlService tsbzGbpxjlService;

    /**
     * 查询干部培训经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbpxjl:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbpxjl tsbzGbpxjl) {
        startPage();
        List<TsbzGbpxjl> list = tsbzGbpxjlService.selectTsbzGbpxjlList(tsbzGbpxjl);
        return getDataTable(list);
    }

    /**
     * 导出干部培训经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbpxjl:export')")
    @Log(title = "干部培训经历", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbpxjl tsbzGbpxjl) {
        List<TsbzGbpxjl> list = tsbzGbpxjlService.selectTsbzGbpxjlList(tsbzGbpxjl);
        ExcelUtil<TsbzGbpxjl> util = new ExcelUtil<TsbzGbpxjl>(TsbzGbpxjl.class);
        return util.exportExcel(list, "gbpxjl");
    }

    /**
     * 获取干部培训经历详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbpxjl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbpxjlService.selectTsbzGbpxjlById(id));
    }

    /**
     * 新增干部培训经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbpxjl:add')")
    @Log(title = "干部培训经历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbpxjl tsbzGbpxjl) {
        return toAjax(tsbzGbpxjlService.insertTsbzGbpxjl(tsbzGbpxjl));
    }

    /**
     * 修改干部培训经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbpxjl:edit')")
    @Log(title = "干部培训经历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbpxjl tsbzGbpxjl) {
        return toAjax(tsbzGbpxjlService.updateTsbzGbpxjl(tsbzGbpxjl));
    }

    /**
     * 删除干部培训经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbpxjl:remove')")
    @Log(title = "干部培训经历", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbpxjlService.deleteTsbzGbpxjlByIds(ids));
    }
}

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
import com.ruoyi.jyykhgl.domain.TsbzJyyyjhd;
import com.ruoyi.jyykhgl.service.ITsbzJyyyjhdService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 研究活动（教研员）Controller
 *
 * @author tsbz
 * @date 2021-01-15
 */
@RestController
@RequestMapping("/jyykhgl/jyyyjhd")
public class TsbzJyyyjhdController extends BaseController {
    @Autowired
    private ITsbzJyyyjhdService tsbzJyyyjhdService;

    /**
     * 查询研究活动（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyyjhd:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyyyjhd tsbzJyyyjhd) {
        startPage();
        List<TsbzJyyyjhd> list = tsbzJyyyjhdService.selectTsbzJyyyjhdList(tsbzJyyyjhd);
        return getDataTable(list);
    }

    /**
     * 导出研究活动（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyyjhd:export')")
    @Log(title = "研究活动（教研员）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyyyjhd tsbzJyyyjhd) {
        List<TsbzJyyyjhd> list = tsbzJyyyjhdService.selectTsbzJyyyjhdList(tsbzJyyyjhd);
        ExcelUtil<TsbzJyyyjhd> util = new ExcelUtil<TsbzJyyyjhd>(TsbzJyyyjhd.class);
        return util.exportExcel(list, "jyyyjhd");
    }

    /**
     * 获取研究活动（教研员）详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyyjhd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyyyjhdService.selectTsbzJyyyjhdById(id));
    }

    /**
     * 新增研究活动（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyyjhd:add')")
    @Log(title = "研究活动（教研员）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyyyjhd tsbzJyyyjhd) {
        return toAjax(tsbzJyyyjhdService.insertTsbzJyyyjhd(tsbzJyyyjhd));
    }

    /**
     * 修改研究活动（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyyjhd:edit')")
    @Log(title = "研究活动（教研员）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyyyjhd tsbzJyyyjhd) {
        return toAjax(tsbzJyyyjhdService.updateTsbzJyyyjhd(tsbzJyyyjhd));
    }

    /**
     * 删除研究活动（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyyjhd:remove')")
    @Log(title = "研究活动（教研员）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyyyjhdService.deleteTsbzJyyyjhdByIds(ids));
    }
}

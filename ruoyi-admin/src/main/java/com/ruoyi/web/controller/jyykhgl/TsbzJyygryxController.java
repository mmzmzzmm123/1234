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
import com.ruoyi.jyykhgl.domain.TsbzJyygryx;
import com.ruoyi.jyykhgl.service.ITsbzJyygryxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个人研修（教研员）Controller
 * 
 * @author tsbz
 * @date 2021-01-16
 */
@RestController
@RequestMapping("/jyykhgl/jyygryx")
public class TsbzJyygryxController extends BaseController
{
    @Autowired
    private ITsbzJyygryxService tsbzJyygryxService;

    /**
     * 查询个人研修（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyygryx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyygryx tsbzJyygryx)
    {
        startPage();
        List<TsbzJyygryx> list = tsbzJyygryxService.selectTsbzJyygryxList(tsbzJyygryx);
        return getDataTable(list);
    }

    /**
     * 导出个人研修（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyygryx:export')")
    @Log(title = "个人研修（教研员）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyygryx tsbzJyygryx)
    {
        List<TsbzJyygryx> list = tsbzJyygryxService.selectTsbzJyygryxList(tsbzJyygryx);
        ExcelUtil<TsbzJyygryx> util = new ExcelUtil<TsbzJyygryx>(TsbzJyygryx.class);
        return util.exportExcel(list, "jyygryx");
    }

    /**
     * 获取个人研修（教研员）详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyygryx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJyygryxService.selectTsbzJyygryxById(id));
    }

    /**
     * 新增个人研修（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyygryx:add')")
    @Log(title = "个人研修（教研员）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyygryx tsbzJyygryx)
    {
        return toAjax(tsbzJyygryxService.insertTsbzJyygryx(tsbzJyygryx));
    }

    /**
     * 修改个人研修（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyygryx:edit')")
    @Log(title = "个人研修（教研员）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyygryx tsbzJyygryx)
    {
        return toAjax(tsbzJyygryxService.updateTsbzJyygryx(tsbzJyygryx));
    }

    /**
     * 删除个人研修（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyygryx:remove')")
    @Log(title = "个人研修（教研员）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJyygryxService.deleteTsbzJyygryxByIds(ids));
    }
}

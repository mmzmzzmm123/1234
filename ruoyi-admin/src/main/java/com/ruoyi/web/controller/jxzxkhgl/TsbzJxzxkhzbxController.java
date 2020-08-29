package com.ruoyi.web.controller.jxzxkhgl;

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
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhzbx;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhzbxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核指标项Controller
 * 
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxzxkhgl/jxzxkhzbx")
public class TsbzJxzxkhzbxController extends BaseController
{
    @Autowired
    private ITsbzJxzxkhzbxService tsbzJxzxkhzbxService;

    /**
     * 查询考核指标项列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhzbx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxkhzbx tsbzJxzxkhzbx)
    {
        startPage();
        List<TsbzJxzxkhzbx> list = tsbzJxzxkhzbxService.selectTsbzJxzxkhzbxList(tsbzJxzxkhzbx);
        return getDataTable(list);
    }

    /**
     * 导出考核指标项列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhzbx:export')")
    @Log(title = "考核指标项", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxkhzbx tsbzJxzxkhzbx)
    {
        List<TsbzJxzxkhzbx> list = tsbzJxzxkhzbxService.selectTsbzJxzxkhzbxList(tsbzJxzxkhzbx);
        ExcelUtil<TsbzJxzxkhzbx> util = new ExcelUtil<TsbzJxzxkhzbx>(TsbzJxzxkhzbx.class);
        return util.exportExcel(list, "jxzxkhzbx");
    }

    /**
     * 获取考核指标项详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhzbx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJxzxkhzbxService.selectTsbzJxzxkhzbxById(id));
    }

    /**
     * 新增考核指标项
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhzbx:add')")
    @Log(title = "考核指标项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxkhzbx tsbzJxzxkhzbx)
    {
        tsbzJxzxkhzbx.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzJxzxkhzbxService.insertTsbzJxzxkhzbx(tsbzJxzxkhzbx));
    }

    /**
     * 修改考核指标项
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhzbx:edit')")
    @Log(title = "考核指标项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxkhzbx tsbzJxzxkhzbx)
    {
        return toAjax(tsbzJxzxkhzbxService.updateTsbzJxzxkhzbx(tsbzJxzxkhzbx));
    }

    /**
     * 删除考核指标项
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhzbx:remove')")
    @Log(title = "考核指标项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJxzxkhzbxService.deleteTsbzJxzxkhzbxByIds(ids));
    }
}

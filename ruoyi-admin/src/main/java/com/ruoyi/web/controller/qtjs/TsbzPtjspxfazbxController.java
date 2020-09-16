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
import com.ruoyi.qtjs.domain.TsbzPtjspxfazbx;
import com.ruoyi.qtjs.service.ITsbzPtjspxfazbxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师评选方案指标Controller
 * 
 * @author ruoyi
 * @date 2020-09-16
 */
@RestController
@RequestMapping("/qtjs/ptjspxfazbx")
public class TsbzPtjspxfazbxController extends BaseController
{
    @Autowired
    private ITsbzPtjspxfazbxService tsbzPtjspxfazbxService;

    /**
     * 查询群体教师评选方案指标列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzPtjspxfazbx tsbzPtjspxfazbx)
    {
        startPage();
        List<TsbzPtjspxfazbx> list = tsbzPtjspxfazbxService.selectTsbzPtjspxfazbxList(tsbzPtjspxfazbx);
        return getDataTable(list);
    }

    /**
     * 导出群体教师评选方案指标列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:export')")
    @Log(title = "群体教师评选方案指标", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzPtjspxfazbx tsbzPtjspxfazbx)
    {
        List<TsbzPtjspxfazbx> list = tsbzPtjspxfazbxService.selectTsbzPtjspxfazbxList(tsbzPtjspxfazbx);
        ExcelUtil<TsbzPtjspxfazbx> util = new ExcelUtil<TsbzPtjspxfazbx>(TsbzPtjspxfazbx.class);
        return util.exportExcel(list, "ptjspxfazbx");
    }

    /**
     * 获取群体教师评选方案指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzPtjspxfazbxService.selectTsbzPtjspxfazbxById(id));
    }

    /**
     * 新增群体教师评选方案指标
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:add')")
    @Log(title = "群体教师评选方案指标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzPtjspxfazbx tsbzPtjspxfazbx)
    {
        return toAjax(tsbzPtjspxfazbxService.insertTsbzPtjspxfazbx(tsbzPtjspxfazbx));
    }

    /**
     * 修改群体教师评选方案指标
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:edit')")
    @Log(title = "群体教师评选方案指标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzPtjspxfazbx tsbzPtjspxfazbx)
    {
        return toAjax(tsbzPtjspxfazbxService.updateTsbzPtjspxfazbx(tsbzPtjspxfazbx));
    }

    /**
     * 删除群体教师评选方案指标
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfa:remove')")
    @Log(title = "群体教师评选方案指标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzPtjspxfazbxService.deleteTsbzPtjspxfazbxByIds(ids));
    }
}

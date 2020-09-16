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
import com.ruoyi.qtjs.domain.TsbzPtjspxfazbxsj;
import com.ruoyi.qtjs.service.ITsbzPtjspxfazbxsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师评选指标数据Controller
 * 
 * @author ruoyi
 * @date 2020-09-16
 */
@RestController
@RequestMapping("/qtjs/ptjspxfazbxsj")
public class TsbzPtjspxfazbxsjController extends BaseController
{
    @Autowired
    private ITsbzPtjspxfazbxsjService tsbzPtjspxfazbxsjService;

    /**
     * 查询群体教师评选指标数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:ptjspxfazbxsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj)
    {
        startPage();
        List<TsbzPtjspxfazbxsj> list = tsbzPtjspxfazbxsjService.selectTsbzPtjspxfazbxsjList(tsbzPtjspxfazbxsj);
        return getDataTable(list);
    }

    /**
     * 导出群体教师评选指标数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:ptjspxfazbxsj:export')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj)
    {
        List<TsbzPtjspxfazbxsj> list = tsbzPtjspxfazbxsjService.selectTsbzPtjspxfazbxsjList(tsbzPtjspxfazbxsj);
        ExcelUtil<TsbzPtjspxfazbxsj> util = new ExcelUtil<TsbzPtjspxfazbxsj>(TsbzPtjspxfazbxsj.class);
        return util.exportExcel(list, "ptjspxfazbxsj");
    }

    /**
     * 获取群体教师评选指标数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:ptjspxfazbxsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzPtjspxfazbxsjService.selectTsbzPtjspxfazbxsjById(id));
    }

    /**
     * 新增群体教师评选指标数据
     */
    @PreAuthorize("@ss.hasPermi('qtjs:ptjspxfazbxsj:add')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj)
    {
        return toAjax(tsbzPtjspxfazbxsjService.insertTsbzPtjspxfazbxsj(tsbzPtjspxfazbxsj));
    }

    /**
     * 修改群体教师评选指标数据
     */
    @PreAuthorize("@ss.hasPermi('qtjs:ptjspxfazbxsj:edit')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj)
    {
        return toAjax(tsbzPtjspxfazbxsjService.updateTsbzPtjspxfazbxsj(tsbzPtjspxfazbxsj));
    }

    /**
     * 删除群体教师评选指标数据
     */
    @PreAuthorize("@ss.hasPermi('qtjs:ptjspxfazbxsj:remove')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzPtjspxfazbxsjService.deleteTsbzPtjspxfazbxsjByIds(ids));
    }
}

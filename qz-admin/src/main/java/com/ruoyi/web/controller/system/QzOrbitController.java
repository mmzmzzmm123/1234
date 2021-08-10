package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.QzOrbit;
import com.ruoyi.system.service.IQzOrbitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 圈子Controller
 * 
 * @author zlx
 * @date 2021-08-10
 */
@RestController
@RequestMapping("/system/orbit")
public class QzOrbitController extends BaseController
{
    @Autowired
    private IQzOrbitService qzOrbitService;

    /**
     * 查询圈子列表
     */
    @PreAuthorize("@ss.hasPermi('system:orbit:list')")
    @GetMapping("/list")
    public TableDataInfo list(QzOrbit qzOrbit)
    {
        startPage();
        List<QzOrbit> list = qzOrbitService.selectQzOrbitList(qzOrbit);
        return getDataTable(list);
    }

    /**
     * 导出圈子列表
     */
    @PreAuthorize("@ss.hasPermi('system:orbit:export')")
    @Log(title = "圈子", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QzOrbit qzOrbit)
    {
        List<QzOrbit> list = qzOrbitService.selectQzOrbitList(qzOrbit);
        ExcelUtil<QzOrbit> util = new ExcelUtil<QzOrbit>(QzOrbit.class);
        return util.exportExcel(list, "圈子数据");
    }

    /**
     * 获取圈子详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:orbit:query')")
    @GetMapping(value = "/{quanId}")
    public AjaxResult getInfo(@PathVariable("quanId") Long quanId)
    {
        return AjaxResult.success(qzOrbitService.selectQzOrbitByQuanId(quanId));
    }

    /**
     * 新增圈子
     */
    @PreAuthorize("@ss.hasPermi('system:orbit:add')")
    @Log(title = "圈子", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QzOrbit qzOrbit)
    {
        return toAjax(qzOrbitService.insertQzOrbit(qzOrbit));
    }

    /**
     * 修改圈子
     */
    @PreAuthorize("@ss.hasPermi('system:orbit:edit')")
    @Log(title = "圈子", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QzOrbit qzOrbit)
    {
        return toAjax(qzOrbitService.updateQzOrbit(qzOrbit));
    }

    /**
     * 删除圈子
     */
    @PreAuthorize("@ss.hasPermi('system:orbit:remove')")
    @Log(title = "圈子", businessType = BusinessType.DELETE)
	@DeleteMapping("/{quanIds}")
    public AjaxResult remove(@PathVariable Long[] quanIds)
    {
        return toAjax(qzOrbitService.deleteQzOrbitByQuanIds(quanIds));
    }
}

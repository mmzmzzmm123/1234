package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.EmrInfoOdsCopy;
import com.ruoyi.system.service.IEmrInfoOdsCopyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * emr原始数据Controller
 * 
 * @author ruoyi
 * @date 2021-11-04
 */
@RestController
@RequestMapping("/system/copy")
public class EmrInfoOdsCopyController extends BaseController
{
    @Autowired
    private IEmrInfoOdsCopyService emrInfoOdsCopyService;

    /**
     * 查询emr原始数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:copy:list')")
    @GetMapping("/list")
    public TableDataInfo list(EmrInfoOdsCopy emrInfoOdsCopy)
    {
        startPage();
        List<EmrInfoOdsCopy> list = emrInfoOdsCopyService.selectEmrInfoOdsCopyList(emrInfoOdsCopy);
        return getDataTable(list);
    }

    /**
     * 导出emr原始数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:copy:export')")
    @Log(title = "emr原始数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EmrInfoOdsCopy emrInfoOdsCopy)
    {
        List<EmrInfoOdsCopy> list = emrInfoOdsCopyService.selectEmrInfoOdsCopyList(emrInfoOdsCopy);
        ExcelUtil<EmrInfoOdsCopy> util = new ExcelUtil<EmrInfoOdsCopy>(EmrInfoOdsCopy.class);
        return util.exportExcel(list, "emr原始数据数据");
    }

    /**
     * 获取emr原始数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:copy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(emrInfoOdsCopyService.selectEmrInfoOdsCopyById(id));
    }

    /**
     * 新增emr原始数据
     */
    @PreAuthorize("@ss.hasPermi('system:copy:add')")
    @Log(title = "emr原始数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmrInfoOdsCopy emrInfoOdsCopy)
    {
        return toAjax(emrInfoOdsCopyService.insertEmrInfoOdsCopy(emrInfoOdsCopy));
    }

    /**
     * 修改emr原始数据
     */
    @PreAuthorize("@ss.hasPermi('system:copy:edit')")
    @Log(title = "emr原始数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmrInfoOdsCopy emrInfoOdsCopy)
    {
        return toAjax(emrInfoOdsCopyService.updateEmrInfoOdsCopy(emrInfoOdsCopy));
    }

    /**
     * 删除emr原始数据
     */
    @PreAuthorize("@ss.hasPermi('system:copy:remove')")
    @Log(title = "emr原始数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(emrInfoOdsCopyService.deleteEmrInfoOdsCopyByIds(ids));
    }
}

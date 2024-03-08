package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.SourceDynamicRelative;
import com.ruoyi.system.service.ISourceDynamicRelativeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车源&动态关联表Controller
 * 
 * @author carol
 * @date 2024-03-06
 */
@RestController
@RequestMapping("/api/relative")
public class SourceDynamicRelativeController extends BaseController
{
    @Autowired
    private ISourceDynamicRelativeService sourceDynamicRelativeService;

    /**
     * 查询车源&动态关联表列表
     */
    @PreAuthorize("@ss.hasPermi('api:relative:list')")
    @GetMapping("/list")
    public TableDataInfo list(SourceDynamicRelative sourceDynamicRelative)
    {
        startPage();
        List<SourceDynamicRelative> list = sourceDynamicRelativeService.selectSourceDynamicRelativeList(sourceDynamicRelative);
        return getDataTable(list);
    }

    /**
     * 导出车源&动态关联表列表
     */
    @PreAuthorize("@ss.hasPermi('api:relative:export')")
    @Log(title = "车源&动态关联表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SourceDynamicRelative sourceDynamicRelative)
    {
        List<SourceDynamicRelative> list = sourceDynamicRelativeService.selectSourceDynamicRelativeList(sourceDynamicRelative);
        ExcelUtil<SourceDynamicRelative> util = new ExcelUtil<SourceDynamicRelative>(SourceDynamicRelative.class);
        util.exportExcel(response, list, "车源&动态关联表数据");
    }

    /**
     * 获取车源&动态关联表详细信息
     */
    @PreAuthorize("@ss.hasPermi('api:relative:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sourceDynamicRelativeService.selectSourceDynamicRelativeById(id));
    }

    /**
     * 新增车源&动态关联表
     */
    @PreAuthorize("@ss.hasPermi('api:relative:add')")
    @Log(title = "车源&动态关联表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SourceDynamicRelative sourceDynamicRelative)
    {
        return toAjax(sourceDynamicRelativeService.insertSourceDynamicRelative(sourceDynamicRelative));
    }

    /**
     * 修改车源&动态关联表
     */
    @PreAuthorize("@ss.hasPermi('api:relative:edit')")
    @Log(title = "车源&动态关联表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SourceDynamicRelative sourceDynamicRelative)
    {
        return toAjax(sourceDynamicRelativeService.updateSourceDynamicRelative(sourceDynamicRelative));
    }

    /**
     * 删除车源&动态关联表
     */
    @PreAuthorize("@ss.hasPermi('api:relative:remove')")
    @Log(title = "车源&动态关联表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sourceDynamicRelativeService.deleteSourceDynamicRelativeByIds(ids));
    }
}

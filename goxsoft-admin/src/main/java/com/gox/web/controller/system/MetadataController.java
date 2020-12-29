package com.gox.web.controller.system;

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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.system.domain.Metadata;
import com.gox.system.service.IMetadataService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 * 文书类基本元数据Controller
 * 
 * @author gox
 * @date 2020-12-28
 */
@RestController
@RequestMapping("/system/metadata")
public class MetadataController extends BaseController
{
    @Autowired
    private IMetadataService metadataService;

    /**
     * 查询文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:metadata:list')")
    @GetMapping("/list")
    public TableDataInfo list(Metadata metadata)
    {
        startPage();
        List<Metadata> list = metadataService.selectMetadataList(metadata);
        return getDataTable(list);
    }

    /**
     * 导出文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:metadata:export')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Metadata metadata)
    {
        List<Metadata> list = metadataService.selectMetadataList(metadata);
        ExcelUtil<Metadata> util = new ExcelUtil<Metadata>(Metadata.class);
        return util.exportExcel(list, "metadata");
    }

    /**
     * 获取文书类基本元数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:metadata:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(metadataService.selectMetadataById(id));
    }

    /**
     * 新增文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('system:metadata:add')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Metadata metadata)
    {
        return toAjax(metadataService.insertMetadata(metadata));
    }

    /**
     * 修改文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('system:metadata:edit')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Metadata metadata)
    {
        return toAjax(metadataService.updateMetadata(metadata));
    }

    /**
     * 删除文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('system:metadata:remove')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(metadataService.deleteMetadataByIds(ids));
    }
}

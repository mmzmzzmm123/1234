package com.gox.basic.controller;

import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.core.page.TableDataInfo;
import com.gox.common.enums.BusinessType;
import com.gox.common.utils.file.Chunk;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.basic.domain.Metadata;
import com.gox.basic.service.IMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @PreAuthorize("@ss.hasPermi('basic:metadata:list')")
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
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Metadata metadata)
    {
        List<Metadata> list = metadataService.selectMetadataList(metadata);
        ExcelUtil<Metadata> util = new ExcelUtil<Metadata>(Metadata.class);
        return util.exportExcel(list, "metadata");
    }
    /**
     * 导出文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export/{ids}")
    public AjaxResult exportItemByIds(@PathVariable Long[] ids)
    {
        return metadataService.exportExcelByIds(ids);
    }
    /**
     * 导出文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据和电子原文", businessType = BusinessType.EXPORT)
    @GetMapping("/export/ele/{ids}")
    public AjaxResult exportItemAndEleByIds(@PathVariable Long[] ids)
    {
        return metadataService.exportExcelAndEleByIds(ids);
    }
    /**
     * 导出 字段模板
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据字段模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export/field")
    public AjaxResult exportFieldExcel()
    {
        ExcelUtil<Metadata> util = new ExcelUtil<Metadata>(Metadata.class);
        return util.exportExcel(new ArrayList<>(), "metadata");
    }
    /**
     * 获取文书类基本元数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(metadataService.selectMetadataById(id));
    }

    /**
     * 新增文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:add')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Metadata metadata)
    {
        return toAjax(metadataService.insertMetadata(metadata));
    }

    /**
     * 修改文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:edit')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Metadata metadata)
    {
        return toAjax(metadataService.updateMetadata(metadata));
    }

    /**
     * 删除文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:remove')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(metadataService.deleteMetadataByIds(ids));
    }

    /**
     * 处理文件上传POST请求
     * 将上传的文件存放到服务器内
     * @param chunk 文件块
     * @param response 响应
     * @return 上传响应状态
     */
    @PreAuthorize("@ss.hasPermi('basic:attributes:add')")
    @Log(title = "电子文件信息", businessType = BusinessType.INSERT)
    @PostMapping("/fileUpload")
    public String uploadPost(@ModelAttribute Chunk chunk, HttpServletResponse response) throws IOException {
        return metadataService.uploadHandle(chunk,response);
    }

    /**
     * 处理文件上传GET请求
     * 验证上传的文件块，是否允许浏览器再次发送POST请求（携带二进制文件的请求流，FormData）
     * @param chunk 文件块
     * @param response 响应
     * @return 文件块
     */
    @GetMapping("/fileUpload")
    public void uploadGet(@ModelAttribute Chunk chunk,HttpServletResponse response){
        response.setStatus(304);
    }
}

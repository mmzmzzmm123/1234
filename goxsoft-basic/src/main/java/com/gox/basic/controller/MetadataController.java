package com.gox.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.gox.basic.domain.vo.ImportFieldMap;
import com.gox.basic.domain.vo.ImportParams;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.basic.service.IFieldsItemService;
import com.gox.basic.utils.ExportUtil;
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.core.page.TableDataInfo;
import com.gox.common.enums.BusinessType;
import com.gox.common.utils.StringUtils;
import com.gox.common.utils.file.Chunk;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.basic.domain.Metadata;
import com.gox.basic.service.IMetadataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文书类基本元数据Controller
 *
 * @author gox
 * @date 2020-12-28
 */
@RestController
@RequestMapping("/system/metadata")
public class MetadataController extends BaseController {
    @Autowired
    private IMetadataService metadataService;
    @Autowired
    private IFieldsItemService iFieldsItemService;
    /**
     * 查询文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:list')")
    @GetMapping("/list")
    public TableDataInfo list(Metadata metadata) {
        startPage();
        String sortField = metadata.getSortField();
        String orderBy = metadata.getOrderBy();
        if (StrUtil.isNotBlank(sortField)) {
            metadata.setSortField(StringUtils.propertyToField(sortField));
        }
        if (StrUtil.isNotBlank(orderBy)) {
            if (orderBy.indexOf("asc") == 0) {
                metadata.setOrderBy("asc");
            } else {
                metadata.setOrderBy("desc");
            }
        }
        List<Metadata> list = metadataService.selectMetadataList(metadata);
        return getDataTable(list);
    }

    /**
     * 导出文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Metadata metadata) throws Throwable {
        List<Metadata> list = metadataService.selectMetadataList(metadata);
        List<TableFieldVo> fieldVos = iFieldsItemService.selectTableFieldByNodeIdAndDeptId(metadata.getNodeId(),metadata.getDeptId());
        String defaultN = System.currentTimeMillis()+".xlsx";
        ExportUtil.exportExcel(list,fieldVos,defaultN);
        return AjaxResult.success(defaultN);
    }

    /**
     * 导出文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult exportItemByIds(@RequestBody String ids) throws Throwable {
        String [] idArr = ids.split(",");
        Long[] id = new Long[idArr.length];
        for (int i = 0; i < idArr.length; i++) {
            id[i] = Long.parseLong(idArr[i]);
        }
        return metadataService.exportExcelByIds(id);
    }

    /**
     * 导出文书类基本元数据列表
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据和电子原文", businessType = BusinessType.EXPORT)
    @PostMapping("/export/ele")
    public AjaxResult exportItemAndEleByIds(@RequestBody String ids) throws Throwable {
        String [] idArr = ids.split(",");
        Long[] id = new Long[idArr.length];
        for (int i = 0; i < idArr.length; i++) {
            id[i] = Long.parseLong(idArr[i]);
        }
        return metadataService.exportExcelAndEleByIds(id);
    }

    /**
     * 导出 字段模板
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:export')")
    @Log(title = "文书类基本元数据字段模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export/field/{nodeId}/{deptId}")
    public AjaxResult exportFieldExcel(@PathVariable Long nodeId,@PathVariable Long deptId) throws Throwable {
        List<TableFieldVo> fieldVos = iFieldsItemService.selectTableFieldByNodeIdAndDeptId(nodeId, deptId);
        String defaultN = System.currentTimeMillis()+".xlsx";
        ExportUtil.exportExcel(new ArrayList<>(),fieldVos,defaultN);
        return AjaxResult.success(defaultN);
    }

    /**
     * 获取文书类基本元数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(metadataService.selectMetadataById(id));
    }

    /**
     * 新增文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:add')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Metadata metadata) {
        return toAjax(metadataService.insertMetadata(metadata));
    }

    /**
     * 修改文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:edit')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Metadata metadata) {
        return toAjax(metadataService.updateMetadata(metadata));
    }

    /**
     * 删除文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:remove')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(metadataService.deleteMetadataByIds(ids));
    }

    /**
     * 处理文件上传POST请求
     * 将上传的文件存放到服务器内
     *
     * @param chunk    文件块
     * @param response 响应
     * @return 上传响应状态
     */
    @PreAuthorize("@ss.hasPermi('basic:attributes:add')")
    @PostMapping("/fileUpload/{nodeId}/{deptId}")
    public String uploadPost(@ModelAttribute Chunk chunk, HttpServletResponse response,@PathVariable Long nodeId,@PathVariable Long deptId) throws IOException {
        return metadataService.uploadHandle(chunk, response,nodeId,deptId);
    }

    /**
     * 处理文件上传GET请求
     * 验证上传的文件块，是否允许浏览器再次发送POST请求（携带二进制文件的请求流，FormData）
     *
     * @param chunk    文件块
     * @param response 响应
     * @return 文件块
     */
    @GetMapping("/fileUpload/{nodeId}/{deptId}")
    public void uploadGet(@ModelAttribute Chunk chunk, HttpServletResponse response, @PathVariable String nodeId, @PathVariable String deptId) {
        response.setStatus(304);
    }
    @ApiOperation(value = "导入处理返回获取的数据头",httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('basic:attributes:add')")
    @GetMapping("/import/{nodeId}/{deptId}/{filename}")
    public AjaxResult uploadHandler(@PathVariable Long nodeId,@PathVariable Long deptId,@PathVariable String filename) throws Throwable {
        return metadataService.handUpload(nodeId,deptId,filename);
    }
    @ApiOperation(value = "确认导入接口",httpMethod = "POST")
    @PreAuthorize("@ss.hasPermi('basic:attributes:add')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importHandler(@RequestBody ImportParams importParams) throws Throwable{
        return metadataService.importHandle(importParams.getNodeId(),importParams.getDeptId(),
                importParams.getParentId(),importParams.getFilename(),importParams.getList());
    }
}

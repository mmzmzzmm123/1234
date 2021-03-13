package com.gox.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.gox.basic.domain.TemplatesPreserve;
import com.gox.basic.domain.Transfer;
import com.gox.basic.domain.vo.ImportParams;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.basic.service.ITemplatesPreserveService;
import com.gox.basic.service.IMetadataTempService;
import com.gox.basic.service.ITransferService;
import com.gox.basic.utils.ExportUtil;
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.core.domain.entity.SysUser;
import com.gox.common.core.domain.model.LoginUser;
import com.gox.common.core.page.TableDataInfo;
import com.gox.common.enums.BusinessType;
import com.gox.common.utils.DateUtils;
import com.gox.common.utils.ServletUtils;
import com.gox.common.utils.StringUtils;
import com.gox.common.utils.file.Chunk;
import com.gox.basic.domain.Metadata;
import com.gox.basic.service.IMetadataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.gox.framework.web.service.TokenService;
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
public class MetadataController extends BaseController {
    @Autowired
    private IMetadataService metadataService;
    @Autowired
    private ITemplatesPreserveService templatesPreserveService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IMetadataTempService metadataTempService;
    @Autowired
    private ITransferService transferService;
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
        //List<TableFieldVo> fieldVos = iFieldsItemService.selectTableFieldByNodeIdAndDeptId(metadata.getNodeId(),metadata.getDeptId());
        List<TemplatesPreserve> templates = templatesPreserveService.selectTemplatesPreserveList(new TemplatesPreserve(metadata.getNodeId(),metadata.getDeptId()));
        String defaultN = System.currentTimeMillis()+".xlsx";
        ExportUtil.exportExcel(list,templates,defaultN);
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
        List<TemplatesPreserve> fieldVos = templatesPreserveService.selectTemplatesPreserveList(new TemplatesPreserve(nodeId, deptId));
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
     * 插件
     * @param metadata
     * @return
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:add')")
    @Log(title = "文书类基本元数据插件", businessType = BusinessType.INSERT)
    @PostMapping("archival")
    public AjaxResult insertArchival(@RequestBody Metadata metadata) throws Throwable {
        return metadataService.insertArchival(metadata);
    }
    @PreAuthorize("@ss.hasPermi('basic:metadata:add')")
    @Log(title = "文书类基本元数据卷内插件", businessType = BusinessType.INSERT)
    @PostMapping("inner")
    public AjaxResult insertArchivalInner(@RequestBody Metadata metadata){
        return AjaxResult.success();
    }
    @PreAuthorize("@ss.hasPermi('basic:metadata:delete')")
    @Log(title = "文书类基本元数据拆件", businessType = BusinessType.DELETE)
    @DeleteMapping("archival")
    public AjaxResult deleteArchival(@RequestBody Metadata metadata){
        return AjaxResult.success();
    }

    /**
     * 拆件卷内
     * @param metadata
     * @return
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:delete')")
    @Log(title = "文书类基本元数据卷内拆件", businessType = BusinessType.DELETE)
    @DeleteMapping("inner")
    public AjaxResult deleteArchivalInner(@RequestBody Metadata metadata){
        return AjaxResult.success();
    }
    /**
     * 修改文书类基本元数据
     */
    @PreAuthorize("@ss.hasPermi('basic:metadata:edit')")
    @Log(title = "文书类基本元数据", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
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


  /**
   * 修改文书类基本元数据
   */
  @PreAuthorize("@ss.hasPermi('basic:metadata:edit')")
  @Log(title = "文书类基本元数据", businessType = BusinessType.UPDATE)
  @PutMapping("/batchEdit")
  public AjaxResult batchEdit(@RequestBody List<Metadata> metadata) {
    System.out.println(metadata);
    return toAjax(metadataService.updateMetadataBatch(metadata));
  }


    /**
     * 获取移交的信息
     *
     * @return 移交信息
     */
    @GetMapping("/getUser/{ids}")
    @PreAuthorize("@ss.hasPermi('basic:metadata:transfer')")
    public AjaxResult getUser(@PathVariable Long[] ids) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Transfer transfer =new Transfer();
        transfer.setTransTime(DateUtils.getNowDate());
        transfer.setTransuser(user.getUserName());
        transfer.setTransdep(user.getDept().getDeptName());
        transfer.setDeptId(user.getDeptId());
        transfer.setTranscount(ids.length);
        return AjaxResult.success(transfer);
    }

    /**
     * 数据移交
     * 1.判断是否档号重复，如果档号重复，移交失败，否则，移交成功
     * 2.将数据采集的移交数据加到临时表，档案管理员审核成功后，再从临时表加到数据管理
     * 3.修改移交表的状态为“待审核”
     *
     */
    @GetMapping("/transfer/{ids}")
    public AjaxResult transferMetadata(@PathVariable Long[] entryids){

        String s2 = String.valueOf(entryids);
        String[] entryidData = s2.split(",");// 1.移交所选数据
        //先判断档号是否重复
        //List<Metadata> list = metadataService.selectMetadataList(null);
        //将数据采集的移交数据加到临时表，档案管理员审核成功后，再从临时表加到数据管理
//        for (int i = 0; i < entryidData.length; i++) {
//            Metadata metadata=metadataService.selectMetadataById(Long.parseLong(entryidData[i]));
//            MetadataTemp temp=new MetadataTemp();
//
//            metadataTempService.insertMetadataTemp(temp);
//        }
//        String transferid=String.valueOf(transdocid);
        //将移交信息写入到trnasferEntry表中
       // int num = metadataService.transfor(entryidData, transferid, TransferEntry.STATUS_AUDIT);

       // if (num > 0) {
            // return new AjaxResult(true, "移交成功", num);
       // }
        // return new AjaxResult(false, "移交失败", null);
        return null;
    }
}

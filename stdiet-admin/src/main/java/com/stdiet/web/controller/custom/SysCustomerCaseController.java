package com.stdiet.web.controller.custom;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.stdiet.common.config.AliyunOSSConfig;
import com.stdiet.common.config.RuoYiConfig;
import com.stdiet.common.constant.Constants;
import com.stdiet.common.exception.file.FileNameLengthLimitExceededException;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.file.FileUploadUtils;
import com.stdiet.common.utils.file.FileUtils;
import com.stdiet.common.utils.file.MimeTypeUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.custom.domain.SysCustomerCaseFile;
import com.stdiet.custom.dto.request.FileRequest;
import com.sun.deploy.net.HttpResponse;
import io.swagger.models.auth.In;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysCustomerCase;
import com.stdiet.custom.service.ISysCustomerCaseService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户案例管理Controller
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
@RestController
@RequestMapping("/custom/customerCase")
public class SysCustomerCaseController extends BaseController
{
    @Autowired
    private ISysCustomerCaseService sysCustomerCaseService;

    /**
     * 查询客户案例管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCustomerCase sysCustomerCase)
    {
        startPage();
        List<SysCustomerCase> list = sysCustomerCaseService.selectSysCustomerCaseList(sysCustomerCase);
        return getDataTable(list);
    }

    /**
     * 导出客户案例管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:export')")
    @Log(title = "客户案例管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCustomerCase sysCustomerCase)
    {
        List<SysCustomerCase> list = sysCustomerCaseService.selectSysCustomerCaseList(sysCustomerCase);
        ExcelUtil<SysCustomerCase> util = new ExcelUtil<SysCustomerCase>(SysCustomerCase.class);
        return util.exportExcel(list, "customerCase");
    }

    /**
     * 获取客户案例管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysCustomerCaseService.selectSysCustomerCaseById(id));
    }

    /**
     * 新增客户案例管理
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:add')")
    @Log(title = "客户案例管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCustomerCase sysCustomerCase)
    {
        return toAjax(sysCustomerCaseService.insertSysCustomerCase(sysCustomerCase));
    }

    /**
     * 修改客户案例管理
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:edit')")
    @Log(title = "客户案例管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCustomerCase sysCustomerCase)
    {
        return toAjax(sysCustomerCaseService.updateSysCustomerCase(sysCustomerCase));
    }

    /**
     * 删除客户案例管理
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:remove')")
    @Log(title = "客户案例管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCustomerCaseService.deleteSysCustomerCaseByIds(ids));
    }

    /**
     * 查询客户案例文件列表
     */
    @GetMapping("/getFileListByCaseId")
    @PreAuthorize("@ss.hasPermi('custom:customerCase:list')")
    public TableDataInfo getFileListByCaseId(@RequestParam("caseId")Long caseId)
    {
        List<SysCustomerCaseFile> list = sysCustomerCaseService.getFileListByCaseId(caseId);
        return getDataTable(list);
    }

    /**
     * 上传文件到OSS返回URL
     */
    @PostMapping("/uploadCaseFile")
    @PreAuthorize("@ss.hasPermi('custom:customerCase:list')")
    public AjaxResult uploadCseFile(MultipartFile file) throws Exception {
        try {
            if(file == null){
                return AjaxResult.error("文件不存在");
            }
            int fileNameLength = file.getOriginalFilename().length();
            if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
            {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

            String fileUrl = AliyunOSSUtils.uploadFileInputSteam(AliyunOSSConfig.casePrefix, file.getOriginalFilename(), file);

            AjaxResult ajax = null;
            if(StringUtils.isNotEmpty(fileUrl)){
                ajax = AjaxResult.success();
                ajax.put("fileUrl", fileUrl);
                ajax.put("fileName", file.getOriginalFilename());
            }else{
                ajax = AjaxResult.error("文件上传失败");
            }
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error("文件上传失败");
        }
    }

    /**
     * 下载案例文件
     * @param
     * @return
     */
    @PostMapping("/downCaseFile")
    @PreAuthorize("@ss.hasPermi('custom:customerCase:list')")
    public void downCaseFile(@RequestBody FileRequest fileRequest, HttpServletRequest request, HttpServletResponse response) {
        /*try{
            InputStream fileStream = AliyunOSSUtils.downloadFile(fileRequest.getFileUrl());
            byte[] body  = new byte[fileStream.available()];
            fileStream.read(body);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attchement;filename=" + fileRequest.getFileName());
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
            return entity;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileRequest.getFileName()));
            FileUtils.writeBytes(AliyunOSSUtils.downloadFile(fileRequest.getFileUrl()), response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
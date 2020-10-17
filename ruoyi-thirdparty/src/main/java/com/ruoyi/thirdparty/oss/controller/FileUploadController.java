package com.ruoyi.thirdparty.oss.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.property.AppProperties;
import com.ruoyi.common.enums.AppConfKey;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.thirdparty.oss.domain.FileUploadResponse;
import com.ruoyi.thirdparty.oss.enums.FileCategoryType;
import com.ruoyi.thirdparty.oss.service.AliyunOssService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/v0.1/files")
public class FileUploadController {

    @Autowired
    private AliyunOssService aliyunOssService;

    @PostMapping(value = "/upload")
    public AjaxResult uploadFile(
            @RequestParam(value = "category") String category,
            @RequestParam(value = "type") String type,
            @RequestParam("file") MultipartFile file) throws Exception {

        FileCategoryType categoryType = FileCategoryType.of(category);
        if (!FileCategoryType.isValidType(categoryType)) {
            return AjaxResult.error("category invalid");
        }

        String fileFormat = FileUtils.getFileFormatWithDot(file.getOriginalFilename(),
                file.getContentType());
        if (StringUtils.isBlank(fileFormat)) {
            return AjaxResult.error("file format error");
        }

        if (!FileCategoryType.isValidFormat(categoryType, fileFormat)) {
            return AjaxResult.error("file format invalid");
        }

        String path = new StringBuilder()
                .append(AppProperties.getProperty(AppConfKey.ALIYUN_OSS_DIR)).append("/")
                .append(type).append("/")
                .append(UUID.randomUUID().toString())
                .append(fileFormat)
                .toString();

        FileUploadResponse ret = aliyunOssService.uploadFile(file.getInputStream(), path, categoryType);
        if (StringUtils.isNotBlank(ret.getErrorMessage())) {
            return AjaxResult.error(ret.getErrorMessage());
        } else {
            return AjaxResult.success(ret);
        }

    }






}

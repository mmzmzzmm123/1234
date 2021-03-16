package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.exception.file.FileNameLengthLimitExceededException;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.file.FileUploadUtils;
import com.stdiet.common.utils.file.MimeTypeUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/custom/fileUpload")
public class SysFileUploadController extends BaseController {

    /**
     * 上传文件到OSS返回URL
     */
    @PostMapping(value = "/{prefix}")
    @PreAuthorize("@ss.hasPermi('custom:file:upload')")
    public AjaxResult uploadCseFile(MultipartFile file, @PathVariable String prefix) throws Exception {

        try {
            if (file == null) {
                return AjaxResult.error("文件不存在");
            }
            int fileNameLength = file.getOriginalFilename().length();
            if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

            String fileUrl = AliyunOSSUtils.uploadFileInputSteam(prefix + '/', DateUtils.getDate() + "/" + file.getOriginalFilename(), file);

            AjaxResult ajax = null;
            if (StringUtils.isNotEmpty(fileUrl)) {
                ajax = AjaxResult.success();
                ajax.put("fileUrl", fileUrl);
                ajax.put("fileName", file.getOriginalFilename());
            } else {
                ajax = AjaxResult.error("文件上传失败");
            }
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error("文件上传失败");
        }
    }
}

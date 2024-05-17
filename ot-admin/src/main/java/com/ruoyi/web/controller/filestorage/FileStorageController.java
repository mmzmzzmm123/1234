package com.ruoyi.web.controller.filestorage;

import com.onethinker.bean.FileInfo;
import com.onethinker.bean.FilePersistInfo;
import com.ruoyi.common.constant.ParameterNames;
import com.ruoyi.common.constant.ServicePathConstant;
import com.ruoyi.common.constant.SystemConst;
import com.ruoyi.common.enums.FileRelationTypeEnum;
import com.ruoyi.common.utils.ip.IpUtils;
import com.onethinker.event.FormFileUploadSuccessEvent;
import com.onethinker.service.FileStorageService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

/**
 * 文件上传
 * @author yangyouqi
 * @date 2024/5/15
 */
@RestController
@RequestMapping("/file")
@Log4j2
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 文件上传
     * @param file 文件
     * @param relationType 关联目标类型
     * @param relationTarget 关联目标id
     * @param name 文件名称
     * @param userId 用户Id
     * @param tenantId 租户id
     * @param appName 上传应用名称
     * @param type 类型
     * @param attr 扩展属性json字符串
     * @return
     */
    @PostMapping(value = ServicePathConstant.PREFIX_PUBLIC_PATH + "/form/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    String uploadFile(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = ParameterNames.RELATION_TYPE, required = false) FileRelationTypeEnum relationType,
            @RequestParam(name = ParameterNames.RELATION_TARGET, required = false) String relationTarget,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = ParameterNames.USER_ID, required = false) String userId,
            @RequestParam(name = ParameterNames.TENANT_ID, defaultValue = SystemConst.DEFAULT_TENANT_ID) String tenantId,
            @RequestParam(name = ParameterNames.APP_NAME) String appName,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "attr",required = false) String attr) {
        if (StringUtils.isBlank(userId)) {
            throw new InvalidParameterException("用户id不能为空");
        }
        FileInfo fileInfo = fileStorageService
                .getFileStorage()
                .serFile(file,name)
                .upload();

        fileInfo.setFileSize(file.getSize());
        fileInfo.setHostName(IpUtils.getHostName());
        fileInfo.setMimeType(type);

        fileInfo.setAppName(appName);
        fileInfo.setCreateUserId(userId);
        fileInfo.setTenantId(tenantId);
        fileInfo.setFileName(name);
        fileInfo.setCreateUserId(userId);
        fileInfo.setTenantId(tenantId);
        fileInfo.setAppName(appName);
        fileInfo.setMimeType(type);

        FilePersistInfo filePersistInfo = new FilePersistInfo();
        filePersistInfo.setFileInfo(fileInfo);
        filePersistInfo.setRelationType(relationType.name());
        filePersistInfo.setRelationTarget(relationTarget);


        String detectMime = fileStorageService.getFileStorage().detectMime(fileInfo);
        fileStorageService.saveFileInfo(filePersistInfo, detectMime);


        FormFileUploadSuccessEvent formFileUploadSuccessEvent;
        // 本地存储才有路径，云端存储直接采用原数据
        if ("local".equals(fileStorageService.getFileStorage().getPlatform())) {
            Path dataFile = Paths.get(fileInfo.getDiskPath());
            formFileUploadSuccessEvent = new FormFileUploadSuccessEvent(dataFile, fileInfo.getId(), fileInfo.getCreateUserId(), fileInfo.getMimeType());
        } else {
            formFileUploadSuccessEvent = new FormFileUploadSuccessEvent(fileInfo, fileInfo.getId(), fileInfo.getCreateUserId(), fileInfo.getMimeType());
        }

        applicationContext.publishEvent(formFileUploadSuccessEvent);

        return fileInfo.getId();
    }


    @EventListener
    @Async
    public void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent) {
        fileStorageService.getFileStorage().handleFormFileUploadSuccessEvent(formFileUploadSuccessEvent);
    }
}

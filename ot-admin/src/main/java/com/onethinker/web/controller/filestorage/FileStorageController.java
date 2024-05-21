package com.onethinker.web.controller.filestorage;

import com.onethinker.file.config.FileStorageProperties;
import com.onethinker.file.domain.FileInfo;
import com.onethinker.file.domain.FileRelation;
import com.onethinker.file.dto.FileInfoFileInfoDTO;
import com.onethinker.file.platform.FileStorage;
import com.onethinker.common.constant.ParameterNames;
import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.constant.SystemConst;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.enums.FileRelationTypeEnum;
import com.onethinker.common.utils.ip.IpUtils;
import com.onethinker.file.event.FormFileUploadSuccessEvent;
import com.onethinker.file.service.FileStorageService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * 文件上传
 * @author yangyouqi
 * @date 2024/5/15
 */
@RestController
@RequestMapping("/file")
@Log4j2
public class FileStorageController extends BaseController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    private final FileStorage fileStorage;
    private final FileStorageService fileStorageService;

    public FileStorageController(@Autowired FileStorageService fileStorageService) {
        this.fileStorage = fileStorageService.getFileStorage();
        this.fileStorageService = fileStorageService;
    }

    /**
     * 文件上传
     * @param file 文件
     * @param relationType 关联目标类型
     * @param relationTarget 关联目标id
     * @param name 文件名称
     * @param userId 用户Id
     * @param tenantId 租户id
     * @param appName 上传应用名称
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
            @RequestParam(name = "attr",required = false) String attr) {
        if (StringUtils.isBlank(userId)) {
            throw new InvalidParameterException("用户id不能为空");
        }
        // 校验文件扩展类型
        String extension = fileStorage.getExtension(name, fileStorageProperties.getAllowExtension());

        FileInfo fileInfo = fileStorage.serFile(file).upload();

        fileInfo.setFileSize(file.getSize());
        fileInfo.setHostName(IpUtils.getHostName());
        fileInfo.setAppName(appName);
        fileInfo.setCreateUserId(userId);
        fileInfo.setTenantId(tenantId);
        fileInfo.setFileName(name);
        fileInfo.setCreateUserId(userId);
        fileInfo.setTenantId(tenantId);
        fileInfo.setAppName(appName);
        fileInfo.setExtension(extension);
        fileInfo.setPlatform(fileStorage.getPlatform());
        fileInfo.setMimeType(fileStorage.queryDetectMime(file,fileStorageProperties.getAllowMime()));
        fileInfo.setDetectMime(fileStorage.queryDetectMime(file,fileStorageProperties.getAllowMime()));
        fileInfo.setFingerprint(fileStorage.queryFingerprint(file));

        FileRelation filePersistInfo = new FileRelation(fileInfo,relationType.name(),relationTarget);

        fileStorageService.saveFileInfo(filePersistInfo, fileInfo);


        FormFileUploadSuccessEvent formFileUploadSuccessEvent;
        // 本地存储才有路径，云端存储直接采用原数据
        if ("local".equals(fileStorage.getPlatform())) {
            Path dataFile = Paths.get(fileInfo.getDiskPath());
            formFileUploadSuccessEvent = new FormFileUploadSuccessEvent(dataFile, fileInfo.getId(), fileInfo.getCreateUserId(), fileInfo.getMimeType());
        } else {
            formFileUploadSuccessEvent = new FormFileUploadSuccessEvent(fileInfo, fileInfo.getId(), fileInfo.getCreateUserId(), fileInfo.getMimeType());
        }

        applicationContext.publishEvent(formFileUploadSuccessEvent);

        return fileInfo.getId();
    }

    /**
     * 文件查询
     * @param fileInfo
     * @return
     */
    @GetMapping(value = ServicePathConstant.PREFIX_PUBLIC_PATH + "/form/query")
    public TableDataInfo queryFile(FileInfo fileInfo) {
        startPage();
        List<FileInfoFileInfoDTO> list = fileStorageService.selectFileList(fileInfo);
        return getDataTable(list);
    }

    @EventListener
    @Async
    public void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent) {
        fileStorage.handleFormFileUploadSuccessEvent(formFileUploadSuccessEvent);
    }
}

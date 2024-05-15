package com.ruoyi.web.controller.filestorage;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.bean.FilePersistInfo;
import com.ruoyi.common.constant.ParameterNames;
import com.ruoyi.common.constant.SystemConst;
import com.ruoyi.common.enums.FileRelationTypeEnum;
import com.ruoyi.event.FormFileUploadSuccessEvent;
import com.ruoyi.service.FileStorageService;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 * @author yangyouqi
 * @date 2024/5/15
 */
@RestController
@RequestMapping("/file")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;


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
    @PostMapping(value = "/form/upload",
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
        FileInfo fileInfo = fileStorageService.getFileStorage().upload(file);
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

        fileStorageService.getFileStorage().publishEvent(fileInfo);

        Map<String, Object> map = StringUtils.isNotBlank(attr) ? JSONObject.parseObject(attr,Map.class) : new HashMap<>(0);
        if (!map.isEmpty()){
            fileStorageService.updateDateAttr(fileInfo.getId(),map);
        }
        return fileInfo.getId();
    }
}

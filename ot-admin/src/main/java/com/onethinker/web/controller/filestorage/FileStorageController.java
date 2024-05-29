package com.onethinker.web.controller.filestorage;

import com.onethinker.common.config.OnethinkerConfig;
import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.constant.SystemConst;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.domain.model.LoginUser;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.enums.FileRelationTypeEnum;
import com.onethinker.common.utils.SecurityUtils;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.common.utils.ip.IpUtils;
import com.onethinker.file.domain.FileInfo;
import com.onethinker.file.domain.FileRelation;
import com.onethinker.file.dto.FileInfoDTO;
import com.onethinker.file.event.FormFileUploadSuccessEvent;
import com.onethinker.file.platform.FileStorage;
import com.onethinker.file.service.FileStorageService;
import com.onethinker.framework.web.service.TokenService;
import com.onethinker.system.service.ISysUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传
 *
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
    private OnethinkerConfig profileConfig;

    private final FileStorage fileStorage;

    private final FileStorageService fileStorageService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorage = fileStorageService.getFileStorage();
        this.fileStorageService = fileStorageService;
    }

    /**
     * 文件上传
     *
     * @param file         文件
     * @param relationType 关联目标类型
     * @param userId       用户Id
     * @param tenantId     租户id
     * @param attr         扩展属性json字符串
     * @return 返回文件内容
     */
    @PostMapping(value = ServicePathConstant.PREFIX_PUBLIC_PATH + "/form/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AjaxResult uploadFile(@RequestParam(name = "file") MultipartFile file,
                                 @RequestParam(name = "relationType") FileRelationTypeEnum relationType,
                                 @RequestParam(name = "userId",required = false) String userId,
                                 @RequestParam(name = "tenantId", defaultValue = SystemConst.DEFAULT_TENANT_ID) String tenantId,
                                 @RequestParam(name = "attr", required = false) String attr) {
        if (StringUtils.isEmpty(userId) && Objects.isNull(SecurityUtils.getUserId())) {
            throw new RuntimeException("用户id不能为空");
        }
        userId = StringUtils.isEmpty(userId) ? String.valueOf(SecurityUtils.getUserId()) : userId;
        // 校验文件扩展类型
        String extension = fileStorage.getExtension(file.getOriginalFilename(), profileConfig.fileStorage.getAllowExtension());

        FileInfo fileInfo = fileStorage.serFile(file).upload();

        fileInfo.setFileSize(file.getSize());
        fileInfo.setHostName(IpUtils.getHostName());
        fileInfo.setTenantId(tenantId);
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setCreateUserId(userId);
        fileInfo.setTenantId(tenantId);
        fileInfo.setExtension(extension);
        fileInfo.setPlatform(fileStorage.getPlatform());
        fileInfo.setMimeType(fileStorage.queryDetectMime(file, profileConfig.fileStorage.getAllowMime()));
        fileInfo.setDetectMime(fileStorage.queryDetectMime(file, profileConfig.fileStorage.getAllowMime()));
        fileInfo.setFingerprint(fileStorage.queryFingerprint(file));

        FileRelation filePersistInfo = new FileRelation(fileInfo, relationType.name(),attr);

        fileStorageService.saveFileInfo(filePersistInfo, fileInfo);


        FormFileUploadSuccessEvent formFileUploadSuccessEvent;
        // 本地存储才有路径，云端存储直接采用原数据
        if (profileConfig.fileStorage.getLocal().getPlatform().equals(fileStorage.getPlatform())) {
            Path dataFile = Paths.get(fileInfo.getDiskPath());
            formFileUploadSuccessEvent = new FormFileUploadSuccessEvent(dataFile, fileInfo.getId(), fileInfo.getCreateUserId(), fileInfo.getMimeType());
        } else {
            formFileUploadSuccessEvent = new FormFileUploadSuccessEvent(fileInfo, fileInfo.getId(), fileInfo.getCreateUserId(), fileInfo.getMimeType());
        }

        LoginUser loginUser = getLoginUser();

        // 头像进行更新用户信息
        if (FileRelationTypeEnum.ADMIN_AVATAR.equals(relationType)) {
            userService.updateUserAvatar(loginUser.getUsername(), fileStorageService.getFileStorage().getDomain() + fileInfo.getPath());
            // 更新缓存用户头像
            loginUser.getUser().setAvatar(fileStorageService.getFileStorage().getDomain() + fileInfo.getPath());
            tokenService.setLoginUser(loginUser);
        }
        applicationContext.publishEvent(formFileUploadSuccessEvent);

        HashMap<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("fileId", fileInfo.getId());
        resultMap.put("fileName", fileInfo.getFileName());
        resultMap.put("imgUrl",fileStorageService.getFileStorage().getDomain() + fileInfo.getPath());
        return success("文件上传成功", resultMap);
    }

    /**
     * 文件查询
     *
     * @param fileId 文件id
     * @return 返回文件内容信息
     */
    @GetMapping(value = ServicePathConstant.PREFIX_PUBLIC_PATH + "/form/query/{fileId}")
    public AjaxResult queryFileByFileId(@PathVariable("fileId") String fileId) {
        FileInfoDTO file = fileStorageService.queryFileByFileId(fileId);
        return success("查询成功", file.getDomain() + file.getPath());
    }

    /**
     * 文件查询
     *
     * @param fileInfo 文件id
     * @return 返回文件内容
     */
    @GetMapping(value = ServicePathConstant.PREFIX_PUBLIC_PATH + "/form/query")
    public TableDataInfo queryFile(FileInfo fileInfo) {
        startPage();
        List<FileInfoDTO> list = fileStorageService.selectFileList(fileInfo);
        return getDataTable(list);
    }

    @EventListener
    @Async
    public void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent) {
        fileStorage.handleFormFileUploadSuccessEvent(formFileUploadSuccessEvent);
    }
}

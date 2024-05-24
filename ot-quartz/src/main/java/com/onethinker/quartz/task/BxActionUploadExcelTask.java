package com.onethinker.quartz.task;

import com.onethinker.bx.domain.BxCustomerInfo;
import com.onethinker.bx.service.IBxCustomerInfoService;
import com.onethinker.common.core.domain.entity.SysUser;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.FileRealtionStatusEnum;
import com.onethinker.common.enums.FileRelationTypeEnum;
import com.onethinker.common.utils.poi.ExcelUtil;
import com.onethinker.file.config.FileStorageProperties;
import com.onethinker.file.dto.FileInfoDTO;
import com.onethinker.file.service.FileStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import oshi.PlatformEnum;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyouqi
 * @date 2024/5/23
 * 上传文件处理
 */
@Component("bxActionUploadExcelTask")
@EnableScheduling
@Log4j2
public class BxActionUploadExcelTask {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private RedisCache redisCache;

    private static final String redisKey = CacheEnum.UPLOAD_FILE_STATUS.getCode();

    @Autowired
    private IBxCustomerInfoService bxCustomerInfoService;

    /**
     * 处理客户信息上传文件
     */
    public void actionUploadCustomerInfoTask() {
        // 读取上传文件信息是否存在客户文件内容
        List<FileInfoDTO> fileInfos = fileStorageService.selectFileList(FileRelationTypeEnum.BX_CUSTOMER, FileRealtionStatusEnum.INIT);
        if (fileInfos.isEmpty()) {
            return;
        }
        fileInfos.stream().filter(this::existsFileUpload).forEach(fileInfoDTO -> {
            // 更新为处理中 设置半小时
            setFileUploadRedis(fileInfoDTO.getId(),60 * 30);
            // 获取对应资源流信息
            try (FileInputStream is = fileStorageService.getFileStorage().download(fileInfoDTO)) {
                ExcelUtil<BxCustomerInfo> util = new ExcelUtil<>(BxCustomerInfo.class);
                List<BxCustomerInfo> bxCustomerInfos = util.importExcel(is);
                bxCustomerInfoService.saveEntry(bxCustomerInfos);
            } catch (Exception e) {
                log.error(e);
            }
            // 文件更新为处理成功
            fileStorageService.updateFileRegionStatusByFileId(fileInfoDTO.getId(),FileRealtionStatusEnum.INIT,FileRealtionStatusEnum.SUCCESS);
            // 更新为处理成功
            setFileUploadRedis(fileInfoDTO.getId(),-1);
        });
    }

    private boolean existsFileUpload(FileInfoDTO fileInfoDTO) {
        // 当前当前有效配置信息
        if (!fileStorageService.getFileStorage().getPlatform().equals(fileInfoDTO.getPlatform())) {
            return false;
        }
        // 校验redis缓存是否存在
        if (!redisCache.hasKey(redisKey + fileInfoDTO.getId())) {
            return false;
        }
        // 校验文件资源是否存在
        return new File(fileInfoDTO.getDiskPath()).exists();
    }

    private void setFileUploadRedis(String fileId,Integer outTime) {
        if (outTime == -1) {
            redisCache.setCacheObject(redisKey + fileId,"success");
        } else {
            redisCache.setCacheObject(redisKey + fileId,"doing",outTime, TimeUnit.MINUTES);
        }
    }
}

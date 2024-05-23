package com.onethinker.quartz.task;

import com.onethinker.bx.domain.BxCustomerInfo;
import com.onethinker.common.core.domain.entity.SysUser;
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

    /**
     * 处理客户信息上传文件
     */
    public void actionUploadCustomerInfoTask() {
        // 读取上传文件信息是否存在客户文件内容
//        List<FileInfoDTO> fileInfos = fileStorageService.selectFileList(FileRelationTypeEnum.BX_CUSTOMER);
//        if (fileInfos.isEmpty()) {
//            return;
//        }
//        for (FileInfoDTO fileInfo : fileInfos) {
//            if ("local".equals(fileInfo.getPlatform())) {
//
//            }
//        }
        File file = new File("C:\\Users\\yyq\\Desktop\\demo\\1.xlsx");
        try (FileInputStream is = new FileInputStream(file)){
            ExcelUtil<BxCustomerInfo> util = new ExcelUtil<>(BxCustomerInfo.class);
            List<BxCustomerInfo> bxCustomerInfos = util.importExcel(is);
            System.out.println(bxCustomerInfos);
        }catch (Exception e) {
            log.error(e);
        }
    }
}

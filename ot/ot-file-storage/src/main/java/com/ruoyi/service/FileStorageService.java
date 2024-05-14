package com.ruoyi.service;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.FileStorageProperties;
import com.ruoyi.platform.FileStorage;
import com.ruoyi.util.Tools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yangyouqi
 * @date 2024/5/14
 * 用来处理文件存储，对接多个平台
 */
@Slf4j
@Getter
@Setter
public class FileStorageService {

    private FileStorageProperties properties;

    private CopyOnWriteArrayList<FileStorage> fileStorageList;

    /**
     * 获取默认的存储平台
     */
    public <T extends FileStorage> T getFileStorage() {
        return this.getFileStorage(properties.getDefaultPlatform());
    }

    /**
     * 获取对应的存储平台
     */
    public <T extends FileStorage> T getFileStorage(String platform) {
        for (FileStorage fileStorage : fileStorageList) {
            if (fileStorage.getPlatform().equals(platform)) {
                return Tools.cast(fileStorage);
            }
        }
        return null;
    }

    /**
     * 获取对应的存储平台，如果存储平台不存在则抛出异常
     */
    public <T extends FileStorage> T getFileStorageVerify(String platform) {
        T fileStorage = this.getFileStorage(platform);
        if (fileStorage == null)
            throw new RuntimeException(StrUtil.format("没有找到对应的存储平台！platform:{}", platform));
        return fileStorage;
    }
}

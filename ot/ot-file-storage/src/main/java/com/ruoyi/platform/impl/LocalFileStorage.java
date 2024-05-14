package com.ruoyi.platform.impl;

import com.ruoyi.FileStorageProperties.LocalConfig;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
@Data
@NoArgsConstructor
public class LocalFileStorage implements FileStorage {
    private String platform;
    private String basePath;

    public LocalFileStorage(LocalConfig config) {
        platform = config.getPlatform();
        basePath = config.getBasePath();
    }

    @Override
    public void close() throws Exception {
        System.out.println("localFileStorage close");
    }
}

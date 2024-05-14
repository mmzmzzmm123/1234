package com.ruoyi.platform.impl;

import com.ruoyi.FileStorageProperties.TencentCosConfig;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 腾讯云 COS 存储
 * @author yangyouqi
 * @date 2024/5/14
 */
@Data
@NoArgsConstructor
public class TencentCosFileStorage implements FileStorage {
    private String platform;
    private String secretId;
    private String secretKey;
    private String region;

    public TencentCosFileStorage(TencentCosConfig config) {
        this.platform = config.getPlatform();
        this.secretId = config.getSecretId();
        this.secretKey = config.getSecretKey();
        this.region = config.getRegion();
    }

    @Override
    public void close() throws Exception {
        System.out.println("关闭系统");
    }
}

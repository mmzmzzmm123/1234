package com.ruoyi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
@Component
@Data
@ConfigurationProperties(prefix = "ruoyi.file-storage")
public class FileStorageProperties {
    /**
     * 默认存储平台
     */
    private String defaultPlatform;

    /**
     * 缩略图后缀，例如【.min.jpg】【.png】
     */
    private String thumbnailSuffix;

    private LocalConfig local;

    private TencentCosConfig tencentCos;

    private QiNiuKodoConfig qiNiuKodo;

    private BaiduBosConfig baiduBos;

    private AliYunOssConfig aliyunOss;

    /**
     * 基本的存储平台配置
     */
    @Data
    @Accessors(chain = true)
    public static class BaseConfig {

        /**
         * 存储平台
         */
        private String platform = "";
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class LocalConfig extends BaseConfig {
        private String basePath;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class AliYunOssConfig extends BaseConfig {
        private String basePath;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class QiNiuKodoConfig extends BaseConfig {
        private String basePath;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TencentCosConfig extends BaseConfig {
        private String enableStorage;
        private String secretId;
        private String secretKey;
        private String region;
        private String bucketName;
        private String domain;
        private String basePath;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class BaiduBosConfig extends BaseConfig {
        private String basePath;
    }


}





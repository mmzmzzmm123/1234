package com.onethinker.common.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author yangyouqi
 */
@Component
@ConfigurationProperties(prefix = "onethinker")
@Data
@Accessors(chain = true)
public class OnethinkerConfig {

    /**
     * 作者信息
     */
    private AuthConfig auth;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;
    /**
     * 上传路径
     */
    private static String profile;

    /**
     * 验证码类型
     */
    private static String captchaType;

    public void setProfile(String profile) {
        OnethinkerConfig.profile = profile;
    }

    public void setCaptchaType(String captchaType) {
        OnethinkerConfig.captchaType = captchaType;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public static String getProfile() {
        return profile;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return profile + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return profile + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return profile + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return profile + "/upload";
    }

    @Data
    @Accessors(chain = true)
    public static class AuthConfig {
        public String name;
        public String url;
        public String email;
    }
}

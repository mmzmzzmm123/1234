package com.ruoyi.platform;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
public interface FileStorage extends AutoCloseable {
    /**
     * 获取平台
     */
    String getPlatform();

    /**
     * 设置平台
     */
    void setPlatform(String platform);

}

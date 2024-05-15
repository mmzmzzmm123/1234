package com.ruoyi.platform;

import com.ruoyi.bean.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

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
     * @param platform 平台标识
     */
    void setPlatform(String platform);

    /**
     * 上传文件
     * @param file 文件内容
     * @return
     */
    FileInfo upload(MultipartFile file);

    /**
     * 检测文件的MIME类型
     * @param fileInfo fileInfo
     * @return
     */
    String detectMime(FileInfo fileInfo);

    /**
     * 推送事件处理
     * @param fileInfo fileInfo
     */
    void publishEvent(FileInfo fileInfo);

    /**
     * 释放相关资源
     */
    default void close() {}


    /**
     * 获取文件全路径（相对存储平台的存储路径）
     *
     * @param basePath 基本路径
     */
    default String getFileKey(String basePath,String fileName) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取文件全路径（相对存储平台的存储路径）
        return String.join("/", basePath, "uploads", "form", String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()), String.valueOf(now.getDayOfMonth()), id, fileName);
    }
}

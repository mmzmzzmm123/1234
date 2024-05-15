package com.ruoyi.platform.impl;

import com.ruoyi.FileStorageProperties.LocalConfig;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public void close() {
        System.out.println("localFileStorage close");
    }

    @Override
    public String upload(MultipartFile file, FileInfo fileInfo) {
        String newKey = getFileKey(basePath,file.getName());
        Path dataFile = Paths.get(newKey);
        if (Files.notExists(dataFile)) {
            //noinspection ResultOfMethodCallIgnored
            dataFile.getParent().toFile().mkdirs();
        }
        try {
            file.transferTo(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newKey;
    }
}

package com.onethinker.file.config;

import com.onethinker.common.config.FileStorageProperties;
import com.onethinker.common.config.OnethinkerConfig;
import com.onethinker.file.platform.FileStorage;
import com.onethinker.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
@Slf4j
@Configuration
public class FileStorageAutoConfiguration {

    private static FileStorageService instance = null;

    /**
     * 存储平台
     */
    private final List<FileStorage> fileStorageList = new ArrayList<>();


    /**
     * 初始化各平台实例
     *
     * @return
     */
    @Bean
    public FileStorageService fileStorageService(@Autowired OnethinkerConfig config) {
        if (instance == null) {
            synchronized (FileStorageAutoConfiguration.class) {
                if (instance == null) {
                    Assert.notNull(config.fileStorage, "fileStorage is null");
                    // 初始化各个存储平台
                    String pageStr = "com.onethinker.file.platform.impl.";
                    buildFileStorage(config.fileStorage, pageStr + "LocalFileStorage");
                    buildFileStorage(config.fileStorage, pageStr + "TencentCosFileStorage");
                    buildFileStorage(config.fileStorage, pageStr + "HuaweiObsFileStorage");
                    // 本体
                    instance = new FileStorageService();
                    instance.setFileStorageList(new CopyOnWriteArrayList<>(fileStorageList));
                }
            }
        }
        return instance;
    }

    /**
     * 根据配置文件内容创建对应平台信息
     *
     * @param config    配置内容
     * @param className 实例化类
     */
    public void buildFileStorage(FileStorageProperties config, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(FileStorageProperties.class);
            constructor.setAccessible(true);
            FileStorage instance = (FileStorage) constructor.newInstance(config);
            fileStorageList.add(instance);
        } catch (Exception e) {
            log.warn("className:{} exception:", className, e);
        }
    }
}

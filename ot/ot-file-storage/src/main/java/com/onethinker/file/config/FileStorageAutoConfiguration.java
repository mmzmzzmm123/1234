package com.onethinker.file.config;

import com.onethinker.file.platform.FileStorage;
import com.onethinker.file.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private List<FileStorage> fileStorageList = new ArrayList<>();


    /**
     * 初始化各平台实例
     * @return
     */
    @Bean
    public FileStorageService fileStorageService(@Autowired FileStorageProperties properties) {
        if (instance == null) {
            synchronized (FileStorageAutoConfiguration.class) {
                if (instance == null) {
                    if (properties == null) throw new RuntimeException("properties 不能为 null");
                    // 初始化各个存储平台
                    String pageStr = "com.onethinker.file.platform.impl.";
                    buildFileStorage(properties,pageStr + "LocalFileStorage");
                    buildFileStorage(properties,pageStr + "TencentCosFileStorage");
                    buildFileStorage(properties,pageStr + "HuaweiObsFileStorage");
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
     * @param config             配置内容
     * @param className          实例化类
     */
    public void buildFileStorage(FileStorageProperties config, String className) {
        if (Objects.nonNull(config)) {
            try {
                Class<?> clazz = Class.forName(className);
                Constructor<?> constructor = clazz.getConstructor(FileStorageProperties.class);
                constructor.setAccessible(true);
                FileStorage instance = (FileStorage) constructor.newInstance(config);
                fileStorageList.add(instance);
            } catch (Exception e) {
                log.warn("className:{} exception:",className,e);
            }
        }
    }
}

package com.ruoyi.service;

import com.ruoyi.FileStorageProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FileStoragePropertiesTest {
    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void test() {
        System.out.println(fileStorageService.getFileStorage().getPlatform());
        System.out.println(fileStorageProperties);
    }
}

package com.ruoyi.service;

import com.ruoyi.bean.FileInfo;
import com.ruoyi.config.FileStorageProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

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
        String filePath = "C:\\Users\\yyq\\Desktop\\demo\\1.jpg";
        File file = new File(filePath);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/plain", inputStream);
            FileInfo upload = fileStorageService.getFileStorage().upload(mockMultipartFile);
            System.out.println(upload);
            System.out.println(fileStorageService.getFileStorage().getPlatform());
            System.out.println(fileStorageProperties);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

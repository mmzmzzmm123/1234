package com.ruoyi.system.service.business;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朱少波
 * @date 2024/1/25/025 16:20
 */
@Service
@AllArgsConstructor
@Slf4j
public class ScreeningService {

    public List<String> analysisFileInfo(MultipartFile file) {
        List<String> phones = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                if (StrUtil.isNotBlank(lineStr)) {
                    phones.add(lineStr.trim());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assert.notEmpty(phones, "文件内容不能为空");
        return phones.stream().distinct().collect(Collectors.toList());
    }


    public void addTask(List<String> phones, String taskName, Integer countryCode) {


    }
}

package com.ruoyi.excelRequest.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ruoyi.excelRequest.service.SendPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class SendPostServiceImpl implements SendPostService {
    @Override
    public void sendPost(String  filePath) {
        File file = new File(filePath);
        List<String> objects = EasyExcel.read(file).doReadAllSync();
        for (String str : objects) {
            log.info("数据:{}", JSON.toJSONString(str));
        }

    }
}


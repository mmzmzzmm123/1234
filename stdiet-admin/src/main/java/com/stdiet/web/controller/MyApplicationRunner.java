package com.stdiet.web.controller;

import com.stdiet.common.config.AliyunOSSConfig;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.custom.mapper.SysCustomerPhysicalSignsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private SysCustomerPhysicalSignsMapper sysCustomerPhysicalSignsMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       /* System.out.println("项目启动调用方法");
        String path = AliyunOSSUtils.uploadFileInputSteam(AliyunOSSConfig.casePrefix,"ceshi.png",new File("D:\\ceshi.png"));
        System.out.println(path);*/
    }
}

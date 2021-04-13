package com.stdiet.web.controller;

import com.stdiet.common.config.AliyunOSSConfig;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.custom.domain.SysNutritionQuestion;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.mapper.SysCustomerPhysicalSignsMapper;
import com.stdiet.custom.mapper.SysNutritionQuestionMapper;
import com.stdiet.custom.mapper.SysWxUserInfoMapper;
import com.stdiet.custom.mapper.SysWxUserLogMapper;
import com.stdiet.custom.service.ISysNutritionQuestionService;
import com.stdiet.custom.utils.LuceneIndexUtils;
import com.stdiet.framework.web.domain.server.Sys;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private SysWxUserLogMapper sysWxUserLogMapper;

    @Autowired
    private SysWxUserInfoMapper sysWxUserInfoMapper;

    @Autowired
    private ISysNutritionQuestionService sysNutritionQuestionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动调用方法");

        /*SysNutritionQuestion sysNutritionQuestion = new SysNutritionQuestion();
        sysNutritionQuestion.setTitle("如何防止猝死");
        sysNutritionQuestion.setContent("少熬夜，少暴饮暴食");
        sysNutritionQuestion.setKey("猝死");
        sysNutritionQuestionService.insertSysNutritionQuestion(sysNutritionQuestion);*/


    }



    /**
     * 从微信用户表中查询openid更新到用户打卡日志表中
     */
    public void dealWxUserLog(){
        /*List<String> phoneList = sysWxUserLogMapper.getAllSysWxUserLogPhone();
        SysWxUserLog sysWxUserLog = new SysWxUserLog();
        if(phoneList.size() > 0){
            for (String phone : phoneList) {
                System.out.println(phone);
                SysWxUserInfo sysWxUserInfo = new SysWxUserInfo();
                sysWxUserInfo.setPhone(phone);
                List<SysWxUserInfo> list = sysWxUserInfoMapper.selectSysWxUserInfoList(sysWxUserInfo);
                if(list != null && list.size() > 0){
                    sysWxUserLog.setOpenid(list.get(0).getOpenid());
                    sysWxUserLog.setPhone(phone);
                    sysWxUserLogMapper.updateSysWxUserLog(sysWxUserLog);
                }
            }
        }*/
    }
}

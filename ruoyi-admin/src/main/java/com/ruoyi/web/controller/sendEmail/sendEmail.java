package com.ruoyi.web.controller.sendEmail;

import invest.lixinger.index.fundamental.VO.fundamentalResult_RootVO;
import invest.lixinger.index.fundamental.getParam_fundamental;
import invest.lixinger.index.fundamental.getResult_fundamental;
import invest.lixinger.index.fundamental.request_fundamental;
import invest.lixinger.utils.netRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static invest.lixinger.index.fundamental.request_fundamental.calculateFundamental;

@Component
public class sendEmail {

    @Autowired
    sendEmailUtils sendEmailUtils;

    // 每个5秒发送邮件
    @Scheduled(cron = "0/5 * * * * *")
    public void sendSimpleMail() throws IOException, ParseException {
        String filePath = request_fundamental.class.getClassLoader().getResource("indexReqParam.yml").getPath();
        Map indexReqParam = new Yaml().load(new FileInputStream(filePath));
        String fundamentalURL = (String) indexReqParam.get("fundamentalURL");
        String paramJson = getParam_fundamental.getSingleIndexParamJson();
        String resultJson = netRequest.jsonNetPost(fundamentalURL, paramJson);
        fundamentalResult_RootVO resultObj = (fundamentalResult_RootVO) getResult_fundamental.getResultObj(resultJson);

        double result=calculateFundamental(resultObj);
//        double result = 1;
        sendEmailUtils.sendSimpleMail(
                "q7800067@qq.com",
                "280014580@qq.com",
                "280014580@qq.com",
                "当日综合百分位为" + result,
                "测试邮件内容");
    }

    public void sendAttachFileMail() {
        sendEmailUtils.sendAttachFileMail(
                "q7800067@qq.com",
                "280014580@qq.com",
                "测试邮件主题sendAttachFileMail",
                "测试邮件内容sendAttachFileMail",
                new File("C:\\Usersxxx\\Desktop\\导出导入\\邮件附件.docx"));
    }

    public void sendMailWithImg() {
        sendEmailUtils.sendMailWithImg(
                "q7800067@qq.com",
                "280014580@qq.com",
                "测试邮件主题(图片)",
                "<div>hello,这是一封带图片资源的邮件：" +
                        "这是图片1：<div><img src='cid:p01'/></div>" +
                        "这是图片2：<div><img src='cid:p02'/></div>" +
                        "</div>",
                new String[]{
                        "C:\\Usersxxx\\Desktop\\Snipaste_2021-01-11_14-41-57.jpg"
                },
                new String[]{"p01", "p02"});
    }


}

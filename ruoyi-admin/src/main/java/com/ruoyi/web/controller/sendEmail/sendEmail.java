package com.ruoyi.web.controller.sendEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@EnableAutoConfiguration
public class sendEmail {

    @Autowired
    sendEmailUtils sendEmailUtils;

    @Scheduled(cron = "0/5 * * * * *")
    public void sendSimpleMail() {
        sendEmailUtils.sendSimpleMail(
                "q7800067@qq.com",
                "280014580@qq.com",
                "280014580@qq.com",
                "测试邮件主题",
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

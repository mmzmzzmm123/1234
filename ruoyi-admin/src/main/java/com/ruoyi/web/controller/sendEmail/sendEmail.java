package com.ruoyi.web.controller.sendEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class sendEmail {

    @Autowired
    sendEmailUtils sendEmailUtils;

    // 每个5秒发送邮件
//    @Scheduled(cron = "0/5 * * * * *")
    @Scheduled(cron = "0 0 16 ? * *")
    public void sendSimpleMail() throws IOException, ParseException {
        Map<String, String> map = sendEmailUtils.wenben();
        List<String> list=new ArrayList<>();
        list.add("280014580@qq.com");
        for (int i = 0; i < list.size(); i++) {
            sendEmailUtils.sendSimpleMail(
                    "q7800067@qq.com",
                    list.get(i),
                    "280014580@qq.com",
                    map.get("subject"),
                    map.get("Text"));
        }

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

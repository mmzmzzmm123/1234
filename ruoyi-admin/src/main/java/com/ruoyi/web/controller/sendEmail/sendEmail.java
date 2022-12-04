package com.ruoyi.web.controller.sendEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ruoyi.web.controller.sendEmail.sendEmailUtils.getText;

// 开机自动启动 https://blog.csdn.net/D_lunar/article/details/126358478?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-4-126358478-blog-126388954.pc_relevant_landingrelevant&spm=1001.2101.3001.4242.3&utm_relevant_index=7
@Component
public class sendEmail {

    @Autowired
    sendEmailUtils sendEmailUtils;

    // 每个5秒发送邮件
//    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0 0 16 ? * *")
    public void sendSimpleMail() throws IOException, ParseException {
        try {
            Map<String, String> map = getText();
            List<String> list = new ArrayList<>();
            list.add("280014580@qq.com");
            System.out.println("map.get(subject)===" + map.get("subject"));
            System.out.println("map.get(Text)===" + map.get("Text"));
            for (int i = 0; i < list.size(); i++) {
                sendEmailUtils.sendSimpleMail(
                        "q7800067@qq.com",
                        list.get(i),
                        "280014580@qq.com",
                        map.get("subject"),
                        map.get("Text"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

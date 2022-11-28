package GYProject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = mybatisTestApplication.class)
@EnableAutoConfiguration
public class sendEmailTest {
    @Autowired
    sendEmailUtils sendEmailUtils;

    @Test
    public void sendSimpleMail() {
        sendEmailUtils.sendSimpleMail(
                "q7800067@qq.com",
                "280014580@qq.com",
                "280014580@qq.com",
                "测试邮件主题",
                "测试邮件内容");
    }

    @Test
    public void sendAttachFileMail() {
        sendEmailUtils.sendAttachFileMail(
                "q7800067@qq.com",
                "280014580@qq.com",
                "测试邮件主题sendAttachFileMail",
                "测试邮件内容sendAttachFileMail",
                new File("C:\\Usersxxx\\Desktop\\导出导入\\邮件附件.docx"));
    }

    @Test
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

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void sendHtmlMailThymeleaf() {
        /*
         * Thymeleaf提供了TemplateEngine来对模板进行渲染，通过Context构造模板中变量需要的值
         */
        Context ctx = new Context();
        ctx.setVariable("username", "sang");
        ctx.setVariable("gender", "男");
        String mail = templateEngine.process("mailtemplate.html", ctx);
        sendEmailUtils.sendHtmlMail(
                "xxx@xxx.com ",
                "xxx@xxx.com ",
                "测试邮件主题Thymeleaf",
                mail);
    }
}

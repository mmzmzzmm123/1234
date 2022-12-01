package com.ruoyi.web.controller.sendEmail;

import invest.lixinger.index.fundamental.VO.fundamentalResult_RootVO;
import invest.lixinger.index.fundamental.getParam_fundamental;
import invest.lixinger.index.fundamental.getResult_fundamental;
import invest.lixinger.index.fundamental.request_fundamental;
import invest.lixinger.utils.netRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static invest.lixinger.index.fundamental.request_fundamental.calculateFundamental;

// https://www.cnblogs.com/ooo0/p/16446829.html
@Component
@MapperScan
public class sendEmailUtils {

    /**
     * JavaMailSender是Spring Boot在MailSenderPropertiesConfiguration类中配置好的，
     * 该类在Mail自动配置类MailSenderAutoConfiguration中导入，因此这里注入JavaMailSender就可以使用了。
     */
    @Autowired
    JavaMailSender javaMailSender;

    /**
     * 发送简单邮件:
     * sendSimpleMail方法的5个参数分别表示邮件发送者、收件人、抄送人、邮件主题以及邮件内容。
     *
     * @param from    发送者
     * @param to      收件人
     * @param cc      抄送人
     * @param subject 主题
     * @param Text    内容
     */
    public void sendSimpleMail(String from, String to, String cc, String subject, String Text) {
        // 简单邮件可以直接构建一个SimpleMailMessage对象进行配置，配置完成后，通过JavaMailSender将邮件发送出去。
        SimpleMailMessage simpMsg = new SimpleMailMessage();
        simpMsg.setFrom(from);
        simpMsg.setTo(to);
        simpMsg.setCc(cc);
        simpMsg.setSubject(subject);
        simpMsg.setText(Text);
        javaMailSender.send(simpMsg);
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("定时任务开启  定时任务开启时间 " + nowTime);

    }

    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param file    附件
     */
    public void sendAttachFileMail(String from, String to, String subject, String content, File file) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            /*
             * 这里使用MimeMessageHelper简化了邮件配置，
             * 它的构造方法的第二个参数true表示构造一个multipart message类型的邮件，
             * multipart message类型的邮件包含多个正文、附件以及内嵌资源，邮件的表现形式更加丰富
             */
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.addAttachment(file.getName(), file);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带图片资源的邮件
     *
     * @param from    发送者
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param srcPath 图片资源路径
     * @param resIds  资源id
     */
    public void sendMailWithImg(String from, String to, String subject, String content, String[] srcPath, String[] resIds) {
        if (srcPath.length != resIds.length) {
            System.out.println("发送失败");
            return;
        }
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//第二个参数true表示邮件正文是HTML格式的，该参数不传默认为false。
            for (int i = 0; i < srcPath.length; i++) {
                FileSystemResource res = new FileSystemResource(new File(srcPath[i]));//通过FileSystemResource构造静态资源
                helper.addInline(resIds[i], res);//调用addInline方法将资源加入邮件对象中
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("发送失败");
        }
    }

    public void sendHtmlMail(String from, String to, String subject, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("发送失败");
        }
    }

    public Map<String, String> wenben() throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        try {

            String fundamentalURL = "https://open.lixinger.com/api/cn/index/fundamental";
            String paramJson = getParam_fundamental.getSingleIndexParamJson();
            String resultJson = netRequest.jsonNetPost(fundamentalURL, paramJson);
            fundamentalResult_RootVO resultObj = (fundamentalResult_RootVO) getResult_fundamental.getResultObj(resultJson);
            String date = paramJson.substring(paramJson.indexOf("date") + 7, paramJson.indexOf("date") + 17);

            double result = calculateFundamental(resultObj);
            String resultFormat = new DecimalFormat("0.00%").format(result);
            String Text = null;
            double result100 = result * 100;
            if (result100 > 45) {
                Text = "▶将资产投资债券或货币基金，利率高就买债券，利率低就买货币基金";
            } else if (35 < result100 && result100 < 45) {
                Text = "▶将资产投资债券或货币基金，利率高就买债券，利率低就买货币基金";
            } else if (25 < result100 && result100 < 35) {
                Text = "▶定投总资金 通过 197 个周定投，定投总资金 = 总资金 - 已投入";
            } else if (15 < result100 && result100 < 25) {
                Text = "▶定投总资金 通过 96 个周定投，定投总资金 = 总资金 - 已投入";
            } else if (10 < result100 && result100 < 15) {
                Text = "▶定投总资金 通过 18 个周定投，定投总资金=总资金-已投入";
                Text += "\n\n▶基金备选池：科创信息、科创创业50、科创50、创业板全指、全指信息、TMT、中创400、中证500、中证军工、国证2000、全指医疗、中小企业300";
                Text += "\n\n▶股票备选池：证券 > 银行";
            } else if (5 < result100 && result100 < 10) {
                Text = "▶梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈";
                Text += "\n\n▶基金备选池：科创信息、科创创业50、科创50、创业板全指、全指信息、TMT、中创400、中证500、中证军工、国证2000、全指医疗、中小企业300";
                Text += "\n\n▶股票备选池：证券>银行";
            }
            String subject = date + "，当日综合百分位为" + resultFormat;
            Text += "\n\n  ▶货币基金优选兴业银行，偏债类基金优选兴业银行、易方达、华夏，股票基金优选易方达 > 广发 > 天弘 > 华夏 > 博时 > 南方 > 富国";
            map.put("subject", subject);
            map.put("Text", Text);
        } catch (Exception e) {
            System.out.println("wenben.exception==" + e.getMessage());
        }


        return map;
    }
}

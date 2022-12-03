package com.ruoyi.web.controller.sendEmail;

import invest.lixinger.index.fundamental.VO.fundamentalResult_RootVO;
import invest.lixinger.index.fundamental.getParam_fundamental;
import invest.lixinger.index.fundamental.getResult_fundamental;
import invest.lixinger.index.fundamental.request_fundamental;
import invest.lixinger.macro.nationalDebt.us.VO.nationalDebtUSResult_DataVO;
import invest.lixinger.macro.nationalDebt.us.VO.nationalDebtUSResult_RootVO;
import invest.lixinger.macro.nationalDebt.us.getParam_nationDebtUS;
import invest.lixinger.macro.nationalDebt.us.getResult_nationDebtUS;
import invest.lixinger.macro.nationalDebt.us.request_nationDebtUS;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    // 产生文本
    public static Map<String, String> getText() throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        try {
            // 基本面判断----------------------
            Map<String, String> fundamentalMap = getTextFundamental();

            double resultFundamental = Double.parseDouble(fundamentalMap.get("resultFundamental"));
            String Text = null;
            double result100 = resultFundamental * 100;
            String Textzhaiquan = "▶将资产投资债券或货币基金，利率高买债券、黄金、白银，利率低买货币基金";
            String Textgupiao = "▶基金备选池：科创信息、科创创业50、科创50、创业板全指、全指信息、TMT、中创400、中证500、中证军工、国证2000、全指医疗、中小企业300、中概互联网\n\n";
            Textgupiao += "▶股票备选池：证券 > 银行";
            if (result100 > 45) {
                Text = Textzhaiquan;
            } else if (35 < result100 && result100 < 45) {
                Text = Textzhaiquan;
            } else if (25 < result100 && result100 < 35) {
                Text = "▶定投总资金 通过 197 个周定投，其中定投总资金 = 总资金 - 已投入\n\n";
                Text += "▶股票基金以大公司为主";
            } else if (15 < result100 && result100 < 25) {
                Text = "▶定投总资金 通过 96 个周定投，其中定投总资金 = 总资金 - 已投入";
                Text += "▶股票基金以中型公司为主";
            } else if (10 < result100 && result100 < 15) {
                Text = "▶定投总资金 通过 18 个周定投，其中定投总资金 = 总资金 - 已投入\n\n";
                Text += Textgupiao;
            } else if (5 < result100 && result100 < 10) {
                Text = "▶梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈\n\n";
                Text += "▶现在离最低点可能还有10~20%的距离，但是为了不错过机会，只能这样\n\n";
                Text += Textgupiao;
            }
            String resultFormat = new DecimalFormat("0.00%").format(resultFundamental);
            String subject = fundamentalMap.get("fundamentalDate") + "，当日信号为" + resultFormat;
            Text += "▶货币基金优选兴业银行，偏债类基金优选兴业银行、易方达、华夏，股票基金优选易方达 > 广发 > 天弘 > 华夏 > 博时 > 南方 > 富国\n\n";
            Text += "▶消费贷款优选工商银行（待探索），消费首优工商、招商银行（待探索），房贷优选xx银行（待探索）\n\n";

            // 美债-------------------------------
            Map<String, String> usDebtMap = getTextUSDebt();
            Text += "▶最近统计的美债为" + usDebtMap.get("meizhiariqi") + "\n\n";
            Text += "▶最近日期美债倒挂比例" + usDebtMap.get("latestDayDebt") + "。一个月钱美债倒挂比例" + usDebtMap.get("oneMonthAgoDebt") + "\n\n";
            if(usDebtMap.get("latestDayDebt").compareTo("0")>1){
                Text +="▶美债已经倒挂，警惕全球金融危机";
            }
            // m1-m2-------------------------------

            // -------------------
            map.put("subject", subject);
            map.put("Text", Text);
        } catch (Exception e) {
            System.out.println("wenben.exception==" + e.getMessage());
        }


        return map;
    }

    // 获取每日基本百分位
    public static Map<String, String> getTextFundamental() throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = request_fundamental.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String fundamentalURL = (String) indexReqParam.get("fundamentalURL");
        String paramJson = getParam_fundamental.getSingleIndexParamJson();
        String resultJson = netRequest.jsonNetPost(fundamentalURL, paramJson);
        fundamentalResult_RootVO resultFundamentalObj = (fundamentalResult_RootVO) getResult_fundamental.getResultObj(resultJson);
        String fundamentalDate = paramJson.substring(paramJson.indexOf("date") + 7, paramJson.indexOf("date") + 17);
        String resultFundamental = String.valueOf(calculateFundamental(resultFundamentalObj));
        map.put("fundamentalDate", fundamentalDate);
        map.put("resultFundamental", resultFundamental);
        return map;
    }

    // 获取美债是否倒挂
    public static Map<String, String> getTextUSDebt() throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = request_nationDebtUS.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String nationaldebtURL = (String) indexReqParam.get("nationaldebtURL");
        String paramJson = getParam_nationDebtUS.getNationDebtUSParamJson();
        String resultJson = netRequest.jsonNetPost(nationaldebtURL, paramJson);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nationalDebtUSResult_RootVO resultObj = (nationalDebtUSResult_RootVO) getResult_nationDebtUS.getResultObj(resultJson);
        nationalDebtUSResult_DataVO latestDayVO = resultObj.getData().get(0);
        nationalDebtUSResult_DataVO oneMonthAgoVO = resultObj.getData().get(resultObj.getData().size() - 1);
        map.put("meizhiariqi", sdf.format(sdf.parse(latestDayVO.getDate())));

        double latestDayy2us = latestDayVO.getMir_y2();
        double latestDayy10us = latestDayVO.getMir_y10();
        double oneMonthAgoy2us = oneMonthAgoVO.getMir_y2();
        double oneMonthAgoy10us = oneMonthAgoVO.getMir_y10();
        String latestDayDebt = String.format("%.2f", (latestDayy2us - latestDayy10us) * 100);
        String oneMonthAgoDebt = String.format("%.2f", (oneMonthAgoy2us - oneMonthAgoy10us) * 100);
        map.put("latestDayDebt", latestDayDebt);
        map.put("oneMonthAgoDebt", oneMonthAgoDebt);
        return map;
    }

    public static void main(String[] args) throws IOException, ParseException {
        Map<String, String> map = getText();
        System.out.println("map.get(subject)===" + map.get("subject"));
        System.out.println("map.get(Text)===" + map.get("Text"));
    }
}

package com.ruoyi.web.controller.sendEmail;

import invest.lixinger.index.fundamental.CN.VO.fundamentalResult_RootVO;
import invest.lixinger.index.fundamental.CN.getParam_fundamental;
import invest.lixinger.index.fundamental.CN.getResult_fundamental;
import invest.lixinger.index.fundamental.CN.request_fundamental;
import invest.lixinger.index.fundamental.US.spx.VO.fundamentalSPXResult_DataVO;
import invest.lixinger.index.fundamental.US.spx.VO.fundamentalSPXResult_RootVO;
import invest.lixinger.index.fundamental.US.spx.getParam_fundamentalSPX;
import invest.lixinger.index.fundamental.US.spx.getResult_fundamentalSPX;
import invest.lixinger.index.fundamental.US.spx.request_fundamentalSPX;
import invest.lixinger.macro.moneySupply.VO.moneySupplyCNParam_DataVO;
import invest.lixinger.macro.moneySupply.VO.moneySupplyCNResult_RootVO;
import invest.lixinger.macro.moneySupply.getParam_moneySupplyCN;
import invest.lixinger.macro.moneySupply.getResult_moneySupplyCN;
import invest.lixinger.macro.moneySupply.request_moneySupplyCN;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static invest.lixinger.index.fundamental.CN.request_fundamental.calculateFundamental;
import static invest.lixinger.index.fundamental.US.spx.request_fundamentalSPX.*;

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
//        simpMsg.setCc(cc);
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
            String Textzhaiquan = "▶现阶段，将资产投资债券、货币基金，利率高则买债券、白银，利率低则买货币基金\n\n";
            String Textgupiao = "▶基金备选池：科创信息、科创创业50、科创50、创业板全指、全指信息、TMT、中创400、中证500、中证军工、国证2000、全指医疗、中小企业300、中概互联网\n\n";
            Textgupiao += "▶股票备选池：证券 > 银行";
            if (result100 > 45) {
                Text = Textzhaiquan;
            } else if (35 < result100 && result100 < 45) {
                Text = Textzhaiquan;
            } else if (25 < result100 && result100 < 35) {
                Text = "\t◆定投总资金 通过 197 个周定投，其中定投总资金 = 总资金 - 已投入\n\n";
                Text += "\t◆股票基金以大公司为主";
            } else if (15 < result100 && result100 < 25) {
                Text = "\t◆定投总资金 通过 96 个周定投，其中定投总资金 = 总资金 - 已投入\n\n";
                Text += "\t◆股票基金以中型公司为主";
            } else if (10 < result100 && result100 < 15) {
                Text = "\t◆定投总资金 通过 18 个周定投，其中定投总资金 = 总资金 - 已投入\n\n";
                Text += Textgupiao;
            } else if (5 < result100 && result100 < 10) {
                Text = "\t◆梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈\n\n";
                Text += "\t◆现在离最低点可能还有10~20%的幅度，但为不错过机会，我将永远相信历史会简单的重复\n\n";
                Text += Textgupiao;
            }
            String resultFormat = new DecimalFormat("0.00%").format(resultFundamental);
            String subject = fundamentalMap.get("fundamentalDate") + "，当日信号为" + resultFormat;
            Text += "▶货币基金优选兴业银行，偏债类基金优选兴业银行、易方达、华夏，股票基金优选易方达 > 广发 > 天弘 > 华夏 > 博时 > 南方 > 富国\n\n";
            Text += "▶信用卡优选工商银行、浦发银行、广发银行（待和同事讨论），日常消费优选工商、招商银行（待和同事讨论），房贷优选xx银行（待和同事讨论）\n\n";

            // 美债-------------------------------
            Map<String, String> usDebtMap = getTextUSDebt();
            Text += "▶美债最新数据日期为：" + usDebtMap.get("meizhiariqi");
            Text += "，美债倒挂比例为：" + usDebtMap.get("latestDayDebt") + "。一个月前美债倒挂比例为：" + usDebtMap.get("oneMonthAgoDebt") + "\n\n";
            if (usDebtMap.get("latestDayDebt").compareTo("0") > 1) {
                Text += "\t结论：美债已经倒挂，警惕全球金融危机\n\n";
            }
            // m1-m2-------------------------------
            Map<String, Object> cnMoneySupplymap = getTextCNMoneySupply();
            double m1 = (double) cnMoneySupplymap.get("m1");
            double m2 = (double) cnMoneySupplymap.get("m2");
            String latestMonthDateMoneySupply = (String) cnMoneySupplymap.get("latestMonthDateMoneySupply");
            double m = m1 - m2;
            if (m < 0) {
                Text += "▶货币宽松量最新数据日期为：" + latestMonthDateMoneySupply + "，实际值为：" + new DecimalFormat("0.00%").format(m) + "\n\n";
                Text += "\t结论：当前环境适合投资股票\n\n";
            } else {
                Text += "▶货币宽松量最新数据日期为：" + latestMonthDateMoneySupply + "，实际值为：" + new DecimalFormat("0.00%").format(m) + "\n\n";
                Text += "\t结论：当前环境不适合投资股票\n\n";
            }
            // SPX信号值
            Map<String, Object> mapSPX = getTextSPX();
            double posSPX = (double) mapSPX.get("posSPX");
            String dateSPX = (String) mapSPX.get("dateSPX");
            Text += "▶美股最新数据日期为：" + dateSPX + "，信号为：" + new DecimalFormat("0.00%").format(posSPX) + "\n\n";
            if (posSPX < 0.3) {
                Text += "\t结论：可考虑美股现在出现的机会\n\n";
            } else {
                Text += "\t结论：美股暂无机会\n\n";
            }
            // -------------------
            map.put("subject", subject);
            map.put("Text", Text);
        } catch (Exception e) {
            System.out.println("wenben.exception==" + e.getMessage());
        }
        return map;
    }

    public static Map<String, Object> getTextSPX() throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream = request_fundamentalSPX.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String fundamentalUSURL = (String) indexReqParam.get("fundamentalUSURL");
        String paramJson = getParam_fundamentalSPX.getSingleIndexParamJson();
        String resultJson = netRequest.jsonNetPost(fundamentalUSURL, paramJson);
        fundamentalSPXResult_RootVO resultObj = (fundamentalSPXResult_RootVO) getResult_fundamentalSPX.getResultObj(resultJson);
        double result = calculateFundamentalSPX(resultObj);
        Map<String, Object> mapSPX = new HashMap<>();
        fundamentalSPXResult_DataVO vo = resultObj.getData().get(0);
        mapSPX.put("dateSPX", sdf.format(sdf.parse(vo.getDate())));
        mapSPX.put("posSPX", result);
        return mapSPX;

    }

    // 获取每日基本百分位
    public static Map<String, String> getTextFundamental() throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = request_fundamental.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String fundamentalCNURL = (String) indexReqParam.get("fundamentalCNURL");
        String paramJson = getParam_fundamental.getSingleIndexParamJson();
        String resultJson = netRequest.jsonNetPost(fundamentalCNURL, paramJson);
        fundamentalResult_RootVO resultFundamentalObj = (fundamentalResult_RootVO) getResult_fundamental.getResultObj(resultJson);
        String fundamentalDate = paramJson.substring(paramJson.indexOf("date") + 7, paramJson.indexOf("date") + 17);
        String resultFundamental = String.valueOf(calculateFundamental(resultFundamentalObj));
        map.put("fundamentalDate", fundamentalDate);
        map.put("resultFundamental", resultFundamental);
        return map;
    }

    public static Map<String, Object> getTextCNMoneySupply() throws IOException, ParseException {
        Map<String, Object> map = new HashMap<>();
        InputStream inputStream = request_moneySupplyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String moneySupplyURL = (String) indexReqParam.get("moneySupplyURL");
        String paramJson = getParam_moneySupplyCN.getParamMoneySupplyCNParamJson();
        String resultJson = netRequest.jsonNetPost(moneySupplyURL, paramJson);
//        String resultJson="{\"code\":1,\"message\":\"success\",\"data\":[{\"date\":\"2022-10-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.118465},\"m1\":{\"t_y2y\":0.057594}}},{\"date\":\"2022-09-30T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.121123},\"m1\":{\"t_y2y\":0.063859}}},{\"date\":\"2022-08-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.122304},\"m1\":{\"t_y2y\":0.060553}}},{\"date\":\"2022-07-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.119855},\"m1\":{\"t_y2y\":0.06684}}}]}";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        moneySupplyCNResult_RootVO resultObj = (moneySupplyCNResult_RootVO) getResult_moneySupplyCN.getResultObj(resultJson);
        moneySupplyCNParam_DataVO latestMonthData = resultObj.getData().get(0);
        map.put("m1", latestMonthData.getM().getM1().getT_y2y());
        map.put("m2", latestMonthData.getM().getM2().getT_y2y());
        map.put("latestMonthDateMoneySupply", sdf.format(sdf.parse(latestMonthData.getDate())));
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, String> map = getText();
        System.out.println("map.get(subject)===" + map.get("subject"));
        System.out.println("map.get(Text)===" + map.get("Text"));
//        Map<String, Object> map = getTextSPX();
//        System.out.println("map.get(Text)===" + map.get("posSPX"));
//        System.out.println("map.get(Text)===");

    }
}

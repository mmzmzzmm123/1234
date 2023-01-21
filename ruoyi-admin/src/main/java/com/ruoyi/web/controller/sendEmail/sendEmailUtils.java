package com.ruoyi.web.controller.sendEmail;

import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNResult_RootVO;
import invest.lixinger.index.fundamental.CN.getParam_indexFundamentalCN;
import invest.lixinger.index.fundamental.CN.getResult_indexFundamentalCN;
import invest.lixinger.index.fundamental.CN.request_indexFundamentalCN;
import invest.lixinger.index.fundamental.HK.HSI.VO.indexFundamentalHSIResult_DataVO;
import invest.lixinger.index.fundamental.HK.HSI.VO.indexFundamentalHSIResult_RootVO;
import invest.lixinger.index.fundamental.HK.HSI.getParam_indexFundamentalHSI;
import invest.lixinger.index.fundamental.HK.HSI.getResult_indexFundamentalHSI;
import invest.lixinger.index.fundamental.HK.HSI.request_indexFundamentalHSI;
import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXResult_DataVO;
import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXResult_RootVO;
import invest.lixinger.index.fundamental.US.spx.getParam_indexFundamentalSPX;
import invest.lixinger.index.fundamental.US.spx.getResult_indexFundamentalSPX;
import invest.lixinger.index.fundamental.US.spx.request_indexFundamentalSPX;
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
import invest.lixinger.macro.priceIndex.china.VO.PriceIndexCNResult_RootVO;
import invest.lixinger.macro.priceIndex.china.getParam_PriceIndexCN;
import invest.lixinger.macro.priceIndex.china.getResult_PriceIndexCN;
import invest.lixinger.macro.priceIndex.china.request_PriceIndexCN;
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

import static invest.lixinger.index.fundamental.CN.request_indexFundamentalCN.calculateFundamentalCN;
import static invest.lixinger.index.fundamental.HK.HSI.request_indexFundamentalHSI.*;
import static invest.lixinger.index.fundamental.US.spx.request_indexFundamentalSPX.*;

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
            Map<String, String> fundamentalMap = getTextFundamentalCN();

            double resultFundamental = Double.parseDouble(fundamentalMap.get("resultFundamental"));
            String Text = "▶现阶段：\n";
            double result100 = resultFundamental * 100;
            String Textzhaiquan = "◆将资产投资债券、货币基金，利率高则买债券、黄金，利率低则买货币基金\n\n";
            String Textgupiao = "◆基金备选池：科创信息、科创创业50、科创50、创业板全指、全指信息、TMT、中创400、中证500、中证军工、国证2000、全指医疗、中小企业300、中概互联网\n";
            Textgupiao += "◆股票备选池：证券 > 银行\n\n";
            if (result100 > 45) {
                Text += Textzhaiquan;
            } else if (35 < result100 && result100 < 45) {
                Text += Textzhaiquan;
            } else if (25 < result100 && result100 < 35) {
                Text += "\t◆定投总资金 通过 197 个周定投，其中定投总资金 = 总资金 - 已投入\n";
                Text += "\t◆股票基金以大公司、主动型基金为主\n\n";
            } else if (15 < result100 && result100 < 25) {
                Text += "\t◆定投总资金 通过 96 个周定投，其中定投总资金 = 总资金 - 已投入\n";
                Text += "\t◆股票基金以中型公司、主动型基金为主\n\n";
            } else if (10 < result100 && result100 < 15) {
                Text += "\t◆定投总资金 通过 18 个周定投，其中定投总资金 = 总资金 - 已投入\n";
                Text += Textgupiao;
            } else if (5 < result100 && result100 < 10) {
                Text += "\t◆梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈梭哈\n";
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
            double m1oneMonthAgo = (double) cnMoneySupplymap.get("m1oneMonthAgo");
            double m2oneMonthAgo = (double) cnMoneySupplymap.get("m2oneMonthAgo");
            String oneMonthAgoDate = (String) cnMoneySupplymap.get("oneMonthAgoDate");
            double MoneMonthAgo = m1oneMonthAgo - m2oneMonthAgo;

            double m1twoMonthAgo = (double) cnMoneySupplymap.get("m1twoMonthAgo");
            double m2twoMonthAgo = (double) cnMoneySupplymap.get("m2twoMonthAgo");
            String twoMonthAgoDate = (String) cnMoneySupplymap.get("twoMonthAgoDate");
            double MtwoMonthAgo = m1twoMonthAgo - m2twoMonthAgo;
            Text += "▶货币宽松量：\n";
            Text += "\t◆最新数据（" + oneMonthAgoDate + "），实际值为：" + new DecimalFormat("0.00%").format(MoneMonthAgo) + "\n";
            Text += "\t◆上一次（" + twoMonthAgoDate + "），实际值为：" + new DecimalFormat("0.00%").format(MtwoMonthAgo) + "\n";
            if (MoneMonthAgo < 0) {
                Text += "\t结论：当前环境适合投资股票\n\n";
            } else {
                Text += "\t结论：当前环境不适合投资股票\n\n";
            }
            // 中国cpi
            Map<String, Object> mapCNCPI =getTextCNPriceIndex();
            String CNCPI0= (String) mapCNCPI.get("CNCPI0");
            String CNCPI1= (String) mapCNCPI.get("CNCPI1");
            String CNCPI2= (String) mapCNCPI.get("CNCPI2");
            String CNCPI3= (String) mapCNCPI.get("CNCPI3");
            String CNCPIDate=(String) mapCNCPI.get("CNCPIDate");
            Text += "▶中国cpi最新数据日期为："+CNCPIDate+"，连续4个月的cpi为：" + CNCPI3 + "、"  + CNCPI2 + "、" + CNCPI1 + "、" + CNCPI0 + "\n\n";
            // SPX信号值----------------
            Map<String, Object> mapSPX = getTextSPX();
            double posSPX = (double) mapSPX.get("posSPX");
            String dateSPX = (String) mapSPX.get("dateSPX");
            Text += "▶美股最新数据日期为：" + dateSPX + "，信号为：" + new DecimalFormat("0.00%").format(posSPX) + "\n\n";
            if (posSPX < 0.3) {
                Text += "\t结论：可考虑美股现在出现的机会\n\n";
            } else {
                Text += "\t结论：美股暂无机会\n\n";
            }
            // HSI信号值----------------
            Map<String, Object> mapHSI = getTextHSI();
            double posHSI = (double) mapHSI.get("posHSI");
            String dateHSI = (String) mapHSI.get("dateHSI");
            Text += "▶港股最新数据日期为：" + dateHSI + "，信号为：" + new DecimalFormat("0.00%").format(posHSI) + "\n\n";
            if (posSPX < 0.3) {
                Text += "\t结论：可考虑港股现在出现的机会\n\n";
            } else {
                Text += "\t结论：港股暂无机会\n\n";
            }
            // -------------------
            map.put("subject", subject);
            map.put("Text", Text);
        } catch (Exception e) {
            System.out.println("wenben.exception==" + e.getMessage());
        }
        return map;
    }

    public static Map<String, Object> getTextHSI() throws IOException, ParseException {
        InputStream inputStream = request_indexFundamentalHSI.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String indexFundamentalHSIURL = (String) indexReqParam.get("indexFundamentalHSIURL");
        String paramJson = getParam_indexFundamentalHSI.getSingleIndexParamJson();
        String resultJson = netRequest.jsonNetPost(indexFundamentalHSIURL, paramJson);
//        String resultJson = "{\"code\":1,\"message\":\"success\",\"data\":[{\"date\":\"2022-12-02T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.3374919720993148,\"cv\":1.3469516090048725,\"cvpos\":0.586515028432169,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.3068458440470567,\"q8v\":1.531597399962167}},\"y20\":{\"median\":{\"avgv\":1.5503823371249033,\"cv\":1.3469516090048725,\"cvpos\":0.36216325703868746,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2339939897597763,\"q5v\":1.4738403193246419,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.348702639130565,\"cv\":13.556077075682696,\"cvpos\":0.5060926076360682,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.528386979175238,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.839932964608655,\"cv\":13.556077075682696,\"cvpos\":0.3872797245290662,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.238407613741876,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.572962603322419,\"cv\":1.9175657433473794,\"cvpos\":0.041429731925264016,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.2009507561861414,\"q5v\":2.5782053163739755,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.7347883106722417,\"cv\":1.9175657433473794,\"cvpos\":0.020660320032408346,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4714766919623146,\"q5v\":3.0080032916934742,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-12-01T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.337488129842245,\"cv\":1.3615071958020988,\"cvpos\":0.617635107679805,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.3068100557379618,\"q8v\":1.531597399962167}},\"y20\":{\"median\":{\"avgv\":1.5504235424577208,\"cv\":1.3615071958020988,\"cvpos\":0.3826985413290113,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2339939897597763,\"q5v\":1.4739967746501792,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.34902458290126,\"cv\":13.482800983381706,\"cvpos\":0.4953271028037383,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.52359908660831,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.840193012388468,\"cv\":13.482800983381706,\"cvpos\":0.37682333873581847,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.238407613741876,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.573228808383335,\"cv\":1.9659755377095853,\"cvpos\":0.05851279967492889,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.201104973866828,\"q5v\":2.578239728415858,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.7351563930233302,\"cv\":1.9659755377095853,\"cvpos\":0.029173419773095625,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4715261142527205,\"q5v\":3.0084819295439207,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-30T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.3375424989699491,\"cv\":1.3458319484820092,\"cvpos\":0.5835026412027631,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.3068100557379618,\"q8v\":1.531597399962167}},\"y20\":{\"median\":{\"avgv\":1.550461815623575,\"cv\":1.3458319484820092,\"cvpos\":0.3596757852077001,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2339939897597763,\"q5v\":1.474030674475416,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.349552204802285,\"cv\":13.159623490172253,\"cvpos\":0.4477854530678586,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.528949521773459,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.840468010773598,\"cv\":13.159623490172253,\"cvpos\":0.3177304964539007,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.238407613741876,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.5735682271711693,\"cv\":2.011695899051669,\"cvpos\":0.07192198293376677,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.2019359473460707,\"q5v\":2.578385768333824,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.73551481702157,\"cv\":2.011695899051669,\"cvpos\":0.035866261398176294,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.472122594129814,\"q5v\":3.0088845492816905,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-29T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.3376092885717945,\"cv\":1.3322176541326016,\"cvpos\":0.5587159691182446,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.3068100557379618,\"q8v\":1.531597399962167}},\"y20\":{\"median\":{\"avgv\":1.5503963031209944,\"cv\":1.3322176541326016,\"cvpos\":0.3436676798378926,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2337622918344127,\"q5v\":1.474030674475416,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.350100061377693,\"cv\":13.110459267917564,\"cvpos\":0.43600162535554654,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.530131803946743,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.841040261701254,\"cv\":13.110459267917564,\"cvpos\":0.30942249240121583,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.238429554937612,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.573879562529384,\"cv\":1.933702341468115,\"cvpos\":0.04632263307598537,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.20238165786661,\"q5v\":2.5785028430059604,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.7359625317834446,\"cv\":1.933702341468115,\"cvpos\":0.023100303951367782,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4729200876306363,\"q5v\":3.0093522216705226,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-28T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.3376856436763582,\"cv\":1.2951693291954591,\"cvpos\":0.46769605851279966,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.3068100557379618,\"q8v\":1.531597399962167}},\"y20\":{\"median\":{\"avgv\":1.5503399782370686,\"cv\":1.2951693291954591,\"cvpos\":0.29118541033434653,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2337544400435774,\"q5v\":1.474030674475416,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.35061803140925,\"cv\":13.044906971577976,\"cvpos\":0.4242177976432345,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.532518833504762,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.841563415803343,\"cv\":13.044906971577976,\"cvpos\":0.3001013171225937,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.239519709613992,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.5742138798470493,\"cv\":1.7782667273012225,\"cvpos\":0.019910605444941082,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.203869982170527,\"q5v\":2.578508632207548,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.736421373899024,\"cv\":1.7782667273012225,\"cvpos\":0.009929078014184398,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4732926432263502,\"q5v\":3.009482423141586,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-25T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.337872292775148,\"cv\":1.3451861445710607,\"cvpos\":0.5800162469536961,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.3069079422076932,\"q8v\":1.531732357638711}},\"y20\":{\"median\":{\"avgv\":1.550069954364045,\"cv\":1.3451861445710607,\"cvpos\":0.35892242252379986,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2334892036017695,\"q5v\":1.4738403193246419,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.351276321200023,\"cv\":13.176011564257152,\"cvpos\":0.4476035743298132,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.535883333988355,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.842405026274959,\"cv\":13.176011564257152,\"cvpos\":0.3182094389305246,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.243612021635956,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.5747157249363433,\"cv\":1.6651699259708335,\"cvpos\":0.008123476848090982,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.2041113860583463,\"q5v\":2.578617772959797,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.7370613050612898,\"cv\":1.6651699259708335,\"cvpos\":0.004051043143609479,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4734313042909957,\"q5v\":3.009777133948894,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-24T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.3378693220798614,\"cv\":1.3272410945365993,\"cvpos\":0.547338480292564,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.306876893127375,\"q8v\":1.531732357638711}},\"y20\":{\"median\":{\"avgv\":1.55011145402169,\"cv\":1.3272410945365993,\"cvpos\":0.3377228525121556,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2334892036017695,\"q5v\":1.4739967746501792,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.351753683002192,\"cv\":13.29072808285143,\"cvpos\":0.4697277529459569,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.536095485162216,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.842742557865401,\"cv\":13.29072808285143,\"cvpos\":0.3411669367909238,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.244903186786798,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.575085158648352,\"cv\":1.744578318394298,\"cvpos\":0.015440877691995123,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.205347392963663,\"q5v\":2.5786298137506027,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.737480971129568,\"cv\":1.744578318394298,\"cvpos\":0.007698541329011345,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4750165758126847,\"q5v\":3.0099645058827873,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-23T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.337947698532758,\"cv\":1.3283787991748301,\"cvpos\":0.5505891913856156,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.306876893127375,\"q8v\":1.531732357638711}},\"y20\":{\"median\":{\"avgv\":1.5501566060394139,\"cv\":1.3283787991748301,\"cvpos\":0.34002026342451874,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2334892036017695,\"q5v\":1.474030674475416,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.352248288321436,\"cv\":13.028518897493079,\"cvpos\":0.42137342543681433,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.536502454346403,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.843056985433273,\"cv\":13.028518897493079,\"cvpos\":0.29827760891590677,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.246225386055801,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.575515861410072,\"cv\":1.7325467437846822,\"cvpos\":0.01381552214546932,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.2062380591602206,\"q5v\":2.5789177723540053,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.7378847196410616,\"cv\":1.7325467437846822,\"cvpos\":0.006889564336372847,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4755840712232438,\"q5v\":3.0100570324302316,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-22T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.3380256128797907,\"cv\":1.3213873318107519,\"cvpos\":0.5323039414872003,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.306876893127375,\"q8v\":1.531732357638711}},\"y20\":{\"median\":{\"avgv\":1.5500917423516278,\"cv\":1.3213873318107519,\"cvpos\":0.32887537993920973,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2332620830590049,\"q5v\":1.474030674475416,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.352809511541079,\"cv\":12.962966601153491,\"cvpos\":0.407557903291345,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.53681306915283,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.843596740096634,\"cv\":12.962966601153491,\"cvpos\":0.28936170212765955,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.247547585324806,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.575942754099165,\"cv\":1.7106013679154553,\"cvpos\":0.011783827712312069,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.2066370632116525,\"q5v\":2.579363454227148,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.73838431451144,\"cv\":1.7106013679154553,\"cvpos\":0.005876393110435664,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.4769332090223637,\"q5v\":3.010936526690377,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"},{\"date\":\"2022-11-21T00:00:00+08:00\",\"pb\":{\"y10\":{\"median\":{\"avgv\":1.3381013223123974,\"cv\":1.3196394649697325,\"cvpos\":0.5274278748476229,\"maxpv\":2.069070952857282,\"maxv\":2.069070952857282,\"minv\":0.8877918779090501,\"q2v\":1.1740450450533184,\"q5v\":1.306876893127375,\"q8v\":1.531732357638711}},\"y20\":{\"median\":{\"avgv\":1.550026403820737,\"cv\":1.3196394649697325,\"cvpos\":0.3264437689969605,\"maxpv\":2.918064158946419,\"maxv\":2.918064158946419,\"minv\":0.8877918779090501,\"q2v\":1.2331470283761374,\"q5v\":1.474030674475416,\"q8v\":1.8763014292101567}}},\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":14.353337533470905,\"cv\":13.044906971577976,\"cvpos\":0.42340511986997154,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":9.535009809828047,\"q2v\":12.214519796396381,\"q5v\":13.537033821980675,\"q8v\":16.32801694462505}},\"y20\":{\"median\":{\"avgv\":14.844203602521942,\"cv\":13.044906971577976,\"cvpos\":0.2996960486322188,\"maxpv\":25.857069329266768,\"maxv\":25.857069329266768,\"minv\":6.905404623928467,\"q2v\":12.380730038531832,\"q5v\":14.247942008687193,\"q8v\":16.853718508321144}}},\"ps_ttm\":{\"y10\":{\"median\":{\"avgv\":2.5763676891965015,\"cv\":1.7458028421161624,\"cvpos\":0.014628199918732222,\"maxpv\":3.5613399498054594,\"maxv\":3.5613399498054594,\"minv\":1.402881599481208,\"q2v\":2.2067166872036834,\"q5v\":2.579631253152183,\"q8v\":2.9509386470367893}},\"y20\":{\"median\":{\"avgv\":3.738881344950361,\"cv\":1.7458028421161624,\"cvpos\":0.00729483282674772,\"maxpv\":8.216795669992614,\"maxv\":8.216795669992614,\"minv\":1.402881599481208,\"q2v\":2.476938820734823,\"q5v\":3.012189796145483,\"q8v\":5.428502607977522}}},\"stockCode\":\"HSI\"}]}";
        indexFundamentalHSIResult_RootVO resultObj = (indexFundamentalHSIResult_RootVO) getResult_indexFundamentalHSI.getAllIndexResultObjHK(resultJson);
        double result = calculateFundamentalHSI(resultObj);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> mapHSI = new HashMap<>();
        indexFundamentalHSIResult_DataVO vo = resultObj.getData().get(0);
        mapHSI.put("dateHSI", sdf.format(sdf.parse(vo.getDate())));
        mapHSI.put("posHSI", result);
        return mapHSI;
    }

    public static Map<String, Object> getTextSPX() throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream = request_indexFundamentalSPX.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String indexFundamentalUSURL = (String) indexReqParam.get("indexFundamentalUSURL");
        String paramJson = getParam_indexFundamentalSPX.getSingleIndexParamJson();
        String resultJson = netRequest.jsonNetPost(indexFundamentalUSURL, paramJson);
        indexFundamentalSPXResult_RootVO resultObj = (indexFundamentalSPXResult_RootVO) getResult_indexFundamentalSPX.getResultObj(resultJson);
        double result = calculateFundamentalSPX(resultObj);
        Map<String, Object> mapSPX = new HashMap<>();
        indexFundamentalSPXResult_DataVO vo = resultObj.getData().get(0);
        mapSPX.put("dateSPX", sdf.format(sdf.parse(vo.getDate())));
        mapSPX.put("posSPX", result);
        return mapSPX;

    }

    // 获取每日基本百分位
    public static Map<String, String> getTextFundamentalCN() throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = request_indexFundamentalCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String indexFundamentalCNURL = (String) indexReqParam.get("indexFundamentalCNURL");
        String paramJson = getParam_indexFundamentalCN.getSingleIndexParamJsonCN();
        String resultJson = netRequest.jsonNetPost(indexFundamentalCNURL, paramJson);
        indexFundamentalCNResult_RootVO resultFundamentalObj = (indexFundamentalCNResult_RootVO) getResult_indexFundamentalCN.getResultObjCN(resultJson);
        String fundamentalDate = paramJson.substring(paramJson.indexOf("date") + 7, paramJson.indexOf("date") + 17);
        String resultFundamental = String.valueOf(calculateFundamentalCN(resultFundamentalObj));
        map.put("fundamentalDate", fundamentalDate);
        map.put("resultFundamental", resultFundamental);
        return map;
    }

    /**
     * 获取货币供应量m1与m2
     *
     */
    public static Map<String, Object> getTextCNMoneySupply() throws IOException, ParseException {
        Map<String, Object> map = new HashMap<>();
        InputStream inputStream = request_moneySupplyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String macroMoneySupplyURL = (String) indexReqParam.get("macroMoneySupplyURL");
        String paramJson = getParam_moneySupplyCN.getParamMoneySupplyCNParamJson();
        String resultJson = netRequest.jsonNetPost(macroMoneySupplyURL, paramJson);
//        String resultJson="{\"code\":1,\"message\":\"success\",\"data\":[{\"date\":\"2022-10-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.118465},\"m1\":{\"t_y2y\":0.057594}}},{\"date\":\"2022-09-30T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.121123},\"m1\":{\"t_y2y\":0.063859}}},{\"date\":\"2022-08-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.122304},\"m1\":{\"t_y2y\":0.060553}}},{\"date\":\"2022-07-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.119855},\"m1\":{\"t_y2y\":0.06684}}}]}";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        moneySupplyCNResult_RootVO resultObj = (moneySupplyCNResult_RootVO) getResult_moneySupplyCN.getResultObj(resultJson);
        moneySupplyCNParam_DataVO oneMonthAgoData = resultObj.getData().get(0);
        map.put("m1oneMonthAgo", oneMonthAgoData.getM().getM1().getT_y2y());
        map.put("m2oneMonthAgo", oneMonthAgoData.getM().getM2().getT_y2y());
        map.put("oneMonthAgoDate", sdf.format(sdf.parse(oneMonthAgoData.getDate())));

        moneySupplyCNParam_DataVO twoMonthAgoData = resultObj.getData().get(1);
        map.put("m1twoMonthAgo", twoMonthAgoData.getM().getM1().getT_y2y());
        map.put("m2twoMonthAgo", twoMonthAgoData.getM().getM2().getT_y2y());
        map.put("twoMonthAgoDate", sdf.format(sdf.parse(twoMonthAgoData.getDate())));
        return map;
    }

    // 获取cpi
    public static Map<String, Object> getTextCNPriceIndex() throws IOException, ParseException {
        InputStream inputStream = request_PriceIndexCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String macroPriceIndexCNURL = (String) indexReqParam.get("macroPriceIndexCNURL");
        String paramJson = getParam_PriceIndexCN.getParamPriceIndexCNParamJson();
        String resultJson = netRequest.jsonNetPost(macroPriceIndexCNURL, paramJson);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PriceIndexCNResult_RootVO resultObj = (PriceIndexCNResult_RootVO) getResult_PriceIndexCN.getResultPriceIndexCNObj(resultJson);
        Map<String, Object> map = new HashMap<>();
        map.put("CNCPI0", new DecimalFormat("0.00%").format(resultObj.getData().get(0).getM().getCpi().getC_y2y()));
        map.put("CNCPI1", new DecimalFormat("0.00%").format(resultObj.getData().get(1).getM().getCpi().getC_y2y()));
        map.put("CNCPI2", new DecimalFormat("0.00%").format(resultObj.getData().get(2).getM().getCpi().getC_y2y()));
        map.put("CNCPI3", new DecimalFormat("0.00%").format(resultObj.getData().get(3).getM().getCpi().getC_y2y()));
        map.put("CNCPIDate",sdf.format(sdf.parse(resultObj.getData().get(0).getDate())));
        return map;
    }


    // 获取美债是否倒挂
    public static Map<String, String> getTextUSDebt() throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = request_nationDebtUS.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String macroNationalDebtURL = (String) indexReqParam.get("macroNationalDebtURL");
        String paramJson = getParam_nationDebtUS.getNationDebtUSParamJson();
        String resultJson = netRequest.jsonNetPost(macroNationalDebtURL, paramJson);
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
//        System.out.println("map.get(Text)===" + map.get("pringIndex1"));
//        System.out.println("map.get(Text)===" + map.get("pringIndex2"));
//        System.out.println("map.get(Text)===" + map.get("pringIndex3"));
        System.out.println("map.get(subject)===" + map.get("subject"));
        System.out.println("map.get(Text)===" + map.get("Text"));


    }
}

package com.renxin.gauge.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.renxin.common.constant.NumConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送验证码工具类
 * @author  : hemingyang
 * @date : 2020-09-17 14:05
 * @version  1.0
 */
@Component
@Slf4j
//@ConditionalOnProperty("spring.mail.host")
public class CodeUtil {

    /**
     * 发送邮件
     **/
    /*@Resource
    private JavaMailSender javaMailSender;*/

    /**
     * 手机号正则表达式
     **/
    private static Pattern PATTERN_PHONE = Pattern.compile("^[1][3-9][0-9]{9}$");

    /**
     * keyid
     **/
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    /**
     * key密钥
     **/
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 产品
     **/
    @Value("${aliyun.sms.product}")
    private String product;

    /**
     * 域名
     **/
    @Value("${aliyun.sms.domain}")
    private String domain;

    /**
     * 签名
     **/
    @Value("${aliyun.sms.signName}")
    private String signName;

    /**
     * 模板代码
     **/
    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    /**
     * 发送手机短信
     */
    public SendSmsResponse phoneNumberCode(String phoneNum, String code) throws ClientException {
        //发送短信流程
        //初始化ascClient需要的几个参数
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
        DefaultAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-
        SendSmsRequest request = new SendSmsRequest();
        request.setSysMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名
        request.setSignName(signName);
        //必填:短信模板
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换 JSON 串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的为
        request.setTemplateParam("{\"code\":" + "'" + code + "'" + "}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //可选:outId 为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
       return acsClient.getAcsResponse(request);
    }

    /**
     * 验证手机号码
     * @param mobiles 手机号
     * @return 是否符合手机号的格式
     */
    public static boolean checkPhoneNum(String mobiles) {
        boolean flag;
        try {
            Matcher m = PATTERN_PHONE.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱地址是否正确
     * @param email 邮箱
     * @return 验证是否符合邮箱格式
     */
    public static boolean checkEmails(String email) {
        boolean flag;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 生成6位随机数验证码
     * @return 验证码
     */
    public static String vcode() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = NumConstant.NUM_ZERO; i < NumConstant.NUM_SIX; i++) {
            //每次随机出一个数字（0-9）
            int r = random.nextInt(NumConstant.NUM_TEN);
           //把每次随机出的数字拼在一起
            stringBuilder.append(r);
        }
        return stringBuilder.toString();
    }

}

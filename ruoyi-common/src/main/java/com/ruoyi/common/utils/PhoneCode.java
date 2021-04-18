package com.ruoyi.common.utils;

import cn.hutool.core.date.DateUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisKey;
import com.ruoyi.common.core.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 通过手机号发送短信
 *
 * @Auther: Wang
 */
public class PhoneCode {

    private static final Logger logger = LoggerFactory.getLogger(PhoneCode.class);

    private static String code;



    /**
     * 阿里云短信服务配置
     *
     * @param phone
     * @return
     */
    public static Boolean getPhonemsg(String phone) {

        String key = RedisKey.BOOKMARK + RedisKey.REGISTER + phone;
        // 短信验证---阿里
        // 设置超时时间-可自行调整
        System.setProperty(PhoneDeploy.defaultConnectTimeout, PhoneDeploy.Timeout);
        System.setProperty(PhoneDeploy.defaultReadTimeout, PhoneDeploy.Timeout);
        // 初始化ascClient需要的几个参数
        final String product = PhoneDeploy.product;// 短信API产品名称（短信产品名固定，无需修改）
        final String domain = PhoneDeploy.domain;// 短信API产品域名（接口地址固定，无需修改）
        // 替换成你的AK
        final String accessKeyId = PhoneDeploy.accessKeyId;// 你的accessKeyId
        final String accessKeySecret = PhoneDeploy.accessKeySecret;// 你的accessKeySecret
        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e1) {
            e1.printStackTrace();
        }

        //获取验证码
        code = vcode();

        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(PhoneDeploy.SignName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(PhoneDeploy.TemplateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{ \"code\":\"" + code + "\"}");  //此处的code和短信模板中的${code}对应
        // 可选-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                // 请求成功
                RedisUtil redisUtil =   new RedisUtil();
                redisUtil.setEx(key,code,Constants.CAPTCHA_PHONE_EXPIRATION, TimeUnit.MINUTES);
                return true;
            } else {
                // 验证码失败 如果验证码出错，会输出错误码告诉你具体原因
                logger.info("手机号:" + phone + " 时间"+DateUtil.now()+" 发送失败!!错误日志:"+sendSmsResponse.getCode());
                return false;
            }
        } catch (ClientException e) {
            e.printStackTrace();
            logger.info("手机号:" + phone + " 时间"+DateUtil.now()+" 发送失败!!");
            return false;
        }
    }

    /**
     * 生成4位随机数验证码
     *
     * @return 验证码
     */
    public static String vcode(){
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            vcode.append((int)(Math.random() * 9));
        }
        return vcode.toString();
    }
}

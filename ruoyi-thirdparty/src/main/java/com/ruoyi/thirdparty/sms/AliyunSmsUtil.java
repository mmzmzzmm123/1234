package com.ruoyi.thirdparty.sms;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author : yelifeng
 * @date : 2020/10/17 0017 14:50 xuc5566@gmail.com
 */
@Slf4j
public class AliyunSmsUtil {

    // 产品名称:云通信短信API产品,开发者无需替换
    private String product = "Dysmsapi";

    // 产品域名,开发者无需替换
    private String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private String accessKeyId = "yourAccessKeyId";

    private String accessKeySecret = "yourAccessKeySecret";

    private AliyunSmsUtil() {

    }

    public static AliyunSmsUtil init(String accessKeyId, String accessKeySecret) {
        return new AliyunSmsUtil(accessKeyId, accessKeySecret);
    }

    public AliyunSmsUtil(String accessKeyId, String accessKeySecret) {
        super();
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public SmsResult sendMsg(String sign,String templateCode,String templateParam, String mobiles) {
        SmsResult smsResult = new SmsResult();
        String orderNo ="YESOULSMS"+ UUID.randomUUID();
        try {
            // 可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            // 初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            // 组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            // 必填:待发送手机号
            request.setPhoneNumbers(mobiles);
            // 必填:短信签名-可在短信控制台中找到
            request.setSignName(sign);
            // 必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            //	 request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");

            request.setTemplateParam(templateParam);

            // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            // request.setSmsUpExtendCode("90997");

            // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(orderNo);

            // hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            log.debug("sms相应代码：-------"+sendSmsResponse.getCode());
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                smsResult.setResult(true);
                smsResult.setOutId(request.getOutId());
            } else {
                smsResult.setResult(false);
                smsResult.setErrorCode(sendSmsResponse.getCode());
            }
        } catch (Exception e) {
            smsResult.setOutId(orderNo);
            smsResult.setResult(false);
            smsResult.setErrorMsg(e.toString());
        }
        return smsResult;
    }
}

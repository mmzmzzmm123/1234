package com.ruoyi.common.agent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vms.v20200902.VmsClient;
import com.tencentcloudapi.vms.v20200902.models.SendCodeVoiceRequest;
import com.tencentcloudapi.vms.v20200902.models.SendCodeVoiceResponse;
// 导入对应VMS模块的client
// 导入要请求接口对应的request response类

/**
 * Tencent Cloud Vms SendCodeVoice
 * https://cloud.tencent.com/document/product/1128/51559
 */
public class SendCodeVoice {
    public static void main(String[] args) {
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 您也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及您的财产安全。
             * CAM密匙查询: https://console.cloud.tencent.com/cam/capi*/
            Credential cred = new Credential("AKIDta1D8mAH8iHZbNs89uPdbIjtmPlqfEWX", "lFUS9alfIekZ7PQBfsoTryoOhjUzEDJ8");
            // 实例化一个http选项，可选，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            // 设置代理
            // httpProfile.setProxyHost("host");
            // httpProfile.setProxyPort(port);
            /* SDK默认使用POST方法。
             * 如果您一定要使用GET方法，可以在这里设置。GET方法无法处理一些较大的请求 */
            httpProfile.setReqMethod("POST");
            /* SDK有默认的超时时间，非必要请不要进行调整
             * 如有需要请在代码中查阅以获取最新的默认值 */
            httpProfile.setConnTimeout(60);
            /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果您访问的是金融区的服务
             * 则必须手动指定域名，例如vms的上海金融区域名： vms.ap-shanghai-fsi.tencentcloudapi.com */
            httpProfile.setEndpoint("vms.tencentcloudapi.com");
            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK默认用TC3-HMAC-SHA256进行签名
             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("TC3-HMAC-SHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以vms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
            VmsClient client = new VmsClient(cred, "ap-guangzhou", clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 您可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendCodeVoiceRequest req = new SendCodeVoiceRequest();
            /* 填充请求参数,这里request对象的成员变量即对应接口的入参
             * 您可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 语音消息控制台：https://console.cloud.tencent.com/vms
             * vms helper：https://cloud.tencent.com/document/product/1128/37720 */
            // 验证码，仅支持填写数字，实际播报语音时，会自动在数字前补充语音文本"您的验证码是"
            String codeMessage = "111111";
            req.setCodeMessage(codeMessage);
            /* 被叫手机号码，采用 e.164 标准，格式为+[国家或地区码][用户号码]
             * 例如：+8613711112222，其中前面有一个+号，86为国家码，13711112222为手机号 */
            String calledNumber = "+8618224037020";
            req.setCalledNumber(calledNumber);
            // 在 [语音控制台] 添加应用后生成的实际SdkAppid，示例如1400006666
            String voiceSdkAppid = "1400421926";
            req.setVoiceSdkAppid(voiceSdkAppid);
            // 播放次数，可选，最多3次，默认2次
            Long playTimes = 3L;
            req.setPlayTimes(playTimes);
            // 用户的 session 内容，腾讯 server 回包中会原样返回
            String sessionContext = "111111";
            req.setSessionContext(sessionContext);
            /* 通过 client 对象调用 SendCodeVoice 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendCodeVoiceResponse 类的实例，与请求对象对应 */
            SendCodeVoiceResponse res = client.SendCodeVoice(req);
            // 输出json格式的字符串回包
            System.out.println(SendCodeVoiceResponse.toJsonString(res));
            // 也可以取出单个值，您可以通过官网接口文档或跳转到response对象的定义处查看返回字段的定义
            System.out.println(res.getRequestId());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}

package com.ruoyi.common.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import com.ruoyi.common.config.CommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class SMSService {

    Logger logger = LoggerFactory.getLogger (this.getClass ());

    @Resource
    private CommonConfig commonConfig;

    private SMSClient client;

    private static int registerTempId = 148418; // 注册模板
    private static int loginTempId = 148417; // 登录模板
    private static int regSucId = 152215; // 注册成功模板
    private static int addEmpId = 151527; // 添加员工模板
    private static int resetEmpPsw = 151221; // 重置员工密码模板
    private static int applyId = 151637; // 报名验证码模板
    private static int patriarchLoginId = 152601; // 家长端登录验证码模板
    private static int publicCodeId = 154801;// 通用的验证码模板
    private static int birthTeacher = 199895; // 教师生日祝福模板
    private static int warn = 199897;// 平台剩余日期提醒模板

    // private static int TEMP_TYPE_NOTICE = 2; // 通知类
    // private static int TEMP_TYPE_AD = 3; // 营销类

    @PostConstruct
    public void init () {
        client = new SMSClient (commonConfig.getSmsSecret (), commonConfig.getSmsAppkey ());
    }

    /**
     * @param mobile
     * @Title: sendRegisterSMS
     * @Description: 发送生日祝福
     */
    public void sendBirthTeacherSMS (String mobile) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (birthTeacher)
                .build ();

        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }

    /**
     * @param mobile
     * @param days
     * @Title: sendRegisterSMS
     * @Description: 平台剩余日期提醒
     */
    public void sendWarnSMS (String mobile,String days) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (warn)
                .addTempPara("days",days).build ();

        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }

    /**
     * @param mobile
     * @param code
     * @Title: sendRegisterSMS
     * @Description: 发送注册验证码
     */
    public void sendRegisterSMS (String mobile, String code) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (registerTempId)
                .addTempPara ("code", code).build ();

        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }

    /**
     * @param mobile
     * @param code
     * @Title: sendLoginSMS
     * @Description: 发送登录验证码
     */
    public void sendLoginSMS (String mobile, String code) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (loginTempId)
                .addTempPara ("code", code).build ();
        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }

    /**
     * @param mobile 接收手机号
     * @Title: sendRegSucSMS
     * @Description: 发送注册成功短信
     */
    public void sendRegSucSMS (String mobile) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (regSucId).build ();
        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }

    /**
     * @param mobile   接收手机号
     * @param password 密码
     * @Title: sendAddEmpSMS
     * @Description: 发送添加员工成功短信
     */
    public void sendAddEmpSMS (String mobile, String password) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (addEmpId)
                .addTempPara ("userName", mobile)
                .addTempPara ("password", password)
                .build ();
        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }

    /**
     * @param mobile
     * @param password
     * @Title: sendResetEmpPswSMS
     * @Description: 重置员工密码短信
     */
    public void sendResetEmpPswSMS (String mobile, String password) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (resetEmpPsw)
                .addTempPara ("password", password)
                .build ();
        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }

    /**
     * @param mobile
     * @param code
     * @Title: sendLoginSMS
     * @Description: 发送登录验证码
     */
    public void sendApplySMS (String mobile, String code) {
        SMSPayload payload = SMSPayload.newBuilder ().setMobileNumber (mobile).setTempId (applyId)
                .addTempPara ("code", code).build ();
        try {
            client.sendTemplateSMS (payload);
        } catch (APIRequestException e) {
            logger.error ("Error response from JPush server. Should review and fix it. ", e);

        } catch (APIConnectionException e) {
            logger.error ("Connection error. Should retry later. ", e);
        }
    }



    public void setClient (SMSClient client) {
        this.client = client;
    }
}

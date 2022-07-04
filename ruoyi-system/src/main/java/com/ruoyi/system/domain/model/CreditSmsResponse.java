package com.ruoyi.system.domain.model;

/**
 * 信用平台短信接口的返回model
 *
 *响应 {
 *     "code": "C00000",
 *     "msg": "查询成功！",
 *     "data": "短信发送成功",
 *     "doing": false,
 *     "successful": true
 * }
 * code
 *  C00000	查询成功
 * 	C00001	查询失败
 * 	C00002	查无数据
 *
 */
public class CreditSmsResponse {

    /**
     * 成功状态码
     */
    public static final String SUCCESS_CODE = "C00000";

    /**
     * 短信模板ID
     */
    public static final String TEMPLATE_ID = "2261012051740";

    /**
     * 状态码，C00000表示接收成功，其他失败
     */
    private String code;
    /**
     * 状态说明
     */
    private String msg;
    /**
     * 返回data
     */
    private String data;
    /**
     * 返回doing
     */
    private boolean doing;
    /**
     * 返回successful
     */
    private boolean successful;

    public static String getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static String getTemplateId() {
        return TEMPLATE_ID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isDoing() {
        return doing;
    }

    public void setDoing(boolean doing) {
        this.doing = doing;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public String toString() {
        return "CreditSmsResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                ", doing=" + doing +
                ", successful=" + successful +
                '}';
    }
}

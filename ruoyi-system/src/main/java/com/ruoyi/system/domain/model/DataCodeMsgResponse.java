package com.ruoyi.system.domain.model;

/**
 * codemsg短信接口的返回model
 */
public class DataCodeMsgResponse {

    /**
     * 成功状态码
     */
    public static final String SUCCESS_CODE = "00000";

    /**
     * 状态码，00000表示接收成功，其他失败
     */
    private String code;
    /**
     * 状态说明
     */
    private String msg;
    /**
     * 成功接口后生成的短信发送批次号
     */
    private String orderNum;

    /**
     * 接收成功的手机号码数量
     */
    private int num;

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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

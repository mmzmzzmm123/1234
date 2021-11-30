package com.ruoyi.system.domain.model;

/**
 * 信用报告
 */
public class CreditReportWrapper {

    private String msg;
    private int code;
    private  CreditReport data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CreditReport getData() {
        return data;
    }

    public void setData(CreditReport data) {
        this.data = data;
    }
}

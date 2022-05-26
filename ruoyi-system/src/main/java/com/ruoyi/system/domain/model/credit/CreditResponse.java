package com.ruoyi.system.domain.model.credit;

/**
 * 泉州市公共信用信息平台（三期）相关接口返回数据
 * @param <T> 核心数据
 */
public class CreditResponse<T> {

    public static final int SUCCESS = 200;

    private int code;
    private String msg;
    private T data;
    private String errors;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}

package com.ruoyi.system.domain.model.credit;

import java.util.List;

/**
 * 金融产品相关接口返回数据
 * @param <T> 核心数据
 */
public class FinanceProductResponse<T> {

    public static final int SUCCESS = 200;

    private int code;
    private String msg;
    private T data;
    private int total;
    private List<T> rows;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "FinanceProductResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}

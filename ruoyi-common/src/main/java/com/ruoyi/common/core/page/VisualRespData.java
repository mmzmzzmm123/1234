package com.ruoyi.common.core.page;

import java.io.Serializable;
import java.util.List;

public class VisualRespData implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 消息状态码 */
    private int code;

    private Boolean success;

    private String msg;

    private List<?> data;

    public VisualRespData() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "VisualRespData{" +
                "code=" + code +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}

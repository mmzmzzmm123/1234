package com.ruoyi.common.core.page;

import java.io.Serializable;
import java.util.List;

public class VisualRespEmbData implements Serializable {
    private static final long serialVersionUID = 1L;

    {
        this.code = 200;
        this.msg = "操作成功";
        this.success = true;
    }


    /** 消息状态码 */
    private int code;

    private Boolean success;

    private String msg;

    private Object data;

    public VisualRespEmbData() {
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VisualRespEmbData{" +
                "code=" + code +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

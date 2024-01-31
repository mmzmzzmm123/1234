package com.ruoyi.system.hglapi;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.exception.GlobalException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author Jing.Zhang
 */
@Data
@Accessors(chain = true)
public class HglApiResult<T> {

    @JSONField(name = "Status")
    private Integer status;

    @JSONField(name = "Data")
    private T data;

    @JSONField(name = "Message")
    private String message;

    @JSONField(name = "RequestId")
    private String requestId;

    public final boolean isSuccess() {
        return Objects.equals(status, 0);
    }

    public final void failedAndThrow() {
        if (!isSuccess()) {
            throw new GlobalException(message);
        }
    }

    public final T getDataOrThrow() {
        if (!isSuccess()) {
            throw new GlobalException(message);
        }
        return this.data;
    }

    public static <R> HglApiResult<R> failed(String message) {
        return (HglApiResult<R>) new HglApiResult<>().setStatus(-1).setMessage(message);
    }
}

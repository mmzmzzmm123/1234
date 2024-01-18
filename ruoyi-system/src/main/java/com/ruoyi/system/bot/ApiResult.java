package com.ruoyi.system.bot;

import com.ruoyi.common.exception.GlobalException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;


@Data
@Accessors(chain = true)
public class ApiResult<T> {

    private Integer code;

    private T data;

    private String msg;

    public final boolean isSuccess() {
        return Objects.equals(code, 200);
    }

    public final void failedAndThrow() {
        if (!isSuccess()) {
            throw new GlobalException(msg);
        }
    }

    public final T getDataOrThrow() {
        if (!isSuccess()) {
            throw new GlobalException(msg);
        }
        return this.data;
    }

    public static <R> ApiResult<R> failed(String message) {
        return (ApiResult<R>) new ApiResult<>().setCode(-1).setMsg(message);
    }
}

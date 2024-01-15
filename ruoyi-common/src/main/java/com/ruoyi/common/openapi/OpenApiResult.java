package com.ruoyi.common.openapi;

import com.ruoyi.common.exception.GlobalException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author Jing.Zhang
 */
@Data
@Accessors(chain = true)
public class OpenApiResult<T> {

    private Integer code;

    private T data;

    private String message;

    public final boolean isSuccess() {
        return Objects.equals(code, 0);
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

    public static <R> OpenApiResult<R> failed(String message) {
        return (OpenApiResult<R>) new OpenApiResult<>().setCode(-1).setMessage(message);
    }
}

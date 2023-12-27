package com.ruoyi.system.extend;

import com.ruoyi.common.exception.GlobalException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author Jing.Zhang
 */
@Data
@Accessors(chain = true)
public class UtTouchResult<T> {

    private Integer code;

    private T data;

    private String message;

    private String busCode;

    public final boolean isSuccess() {
        return Objects.equals(code, 1);
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

    public static <R> UtTouchResult<R> failed(String message) {
        return (UtTouchResult<R>) new UtTouchResult<>().setCode(-1).setMessage(message);
    }
}

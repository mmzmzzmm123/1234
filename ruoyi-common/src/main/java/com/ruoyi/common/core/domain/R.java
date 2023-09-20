package com.ruoyi.common.core.domain;

import java.io.Serializable;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 响应信息主体
 *
 * @author ruoyi
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = HttpStatus.SUCCESS;
    public static final String SUCCESS_TIP = "SUCCESS";

    /**
     * 失败
     */
    public static final int FAIL = HttpStatus.ERROR;
    public static final String FAIL_TIP = "ERROR";

    /**
     * 警告
     */
    public static final int WARN = HttpStatus.WARN_WX;
    public static final String WARN_TIP = "WARN";

    @ApiModelProperty("响应码")
    private int code;

    @ApiModelProperty("响应信息")
    private String msg;

    @ApiModelProperty("返回数据")
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, SUCCESS_TIP);
    }

    public static <T> R<T> ok(String msg) {
        return restResult(null, SUCCESS, msg);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, SUCCESS_TIP);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, FAIL_TIP);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, FAIL_TIP);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> warn() {
        return restResult(null, WARN, WARN_TIP);
    }

    public static <T> R<T> warn(String msg) {
        return restResult(null, WARN, msg);
    }

    public static <T> R<T> warn(T Data) {
        return restResult(Data, WARN, WARN_TIP);
    }

    public static <T> R<T> warn(T Data, String msg) {
        return restResult(Data, WARN, msg);
    }

    public static <T> R<T> warn(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> warn(int code, T Data, String msg) {
        return restResult(Data, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


    /**
     * 判断R返回状态是否成功
     *
     * @param r 返回对象
     * @return 结果
     */
    public static boolean isSuccess(R r) {
        return StringUtils.isNotNull(r) && R.SUCCESS == r.getCode();
    }

    /**
     * 判断R返回状态是否警告
     *
     * @param r 返回对象
     * @return 结果
     */
    public static boolean isWarn(R r) {
        return StringUtils.isNotNull(r) && R.WARN == r.getCode();
    }

    /**
     * 判断R返回状态是否失败
     *
     * @param r 返回对象
     * @return 结果
     */
    public static boolean isError(R r) {
        return !isSuccess(r);
    }

    public static <T> R<T> transform(R<?> oldResult) {
        R<T> result = new R<>();
        result.setMsg(oldResult.getMsg());
        result.setCode(oldResult.getCode());
        return result;
    }
}

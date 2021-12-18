package vip.carpool.api.gateway.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vip.carpool.api.gateway.common.api.ApiCode;
import vip.carpool.api.gateway.common.api.ApiResult;

import java.util.Objects;


/**
 * @ClassName 全局异常处理
 * @author 刘荣华
 * @Date 2019/6/27
 **/
@Slf4j
@ControllerAdvice
public class GatewayExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult allError(Exception exception) {
        exception.printStackTrace();
        return ApiResult.fail(ApiCode.FAIL, exception.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @returns
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        StringBuffer msg = new StringBuffer(str[1] + ":");
        msg.append(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ApiResult.fail(msg.toString());
    }

    /**
     * 处理自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ApiResult badRequestException(BadRequestException e) {
        // 打印堆栈信息
        e.printStackTrace();
        log.error(e.getMessage());
        return ApiResult.fail(e.getMessage());
    }
}

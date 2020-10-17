package com.ruoyi.framework.config;

/**
 * @author : yelifeng
 * @date : 2020/10/17 0017 16:12 xuc5566@gmail.com
 */
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.Locale;

@ControllerAdvice
public class GlobalHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalHandler.class);

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler(Throwable.class)
    @ResponseBody //表示拦截json
    public AjaxResult<String> exceptionHandler(Exception e) {
        String logMsg = "";
        AjaxResult<String> ret = null;

        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            logMsg = bizException.getMessage();
            ret = AjaxResult.error(bizException.getError_code(), getMessage(bizException.getError_code()));
        } else if (e instanceof MethodArgumentNotValidException) {
            logMsg = e.getMessage();
            ret = AjaxResult.error("comm.argument.not.valid", getMessage("comm.argument.not.valid"));
        } else {
            ret = AjaxResult.error(HttpStatus.ERROR, "系统内部错误");
        }
        log.error(logMsg, e);
        return ret;

    }

    public String getMessage(String key, String... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
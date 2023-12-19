package com.ruoyi.common.exception.taskset;

import com.ruoyi.common.exception.base.BaseException;

public class ExcelException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ExcelException(String code, String defaultMessage)
    {
        super("excel", code, null, defaultMessage);
    }
}

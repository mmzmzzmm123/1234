package com.ruoyi.common.exception.taskset;

import com.ruoyi.common.exception.base.BaseException;

public class InputException extends BaseException {
    private static final long serialVersionUID = 1L;

    public InputException(String code, String defaultMessage)
    {
        super("input", code, null, defaultMessage);
    }
}

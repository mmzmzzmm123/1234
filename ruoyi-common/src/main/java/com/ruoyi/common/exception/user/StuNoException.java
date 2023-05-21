package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.base.BaseException;

public class StuNoException extends UserException {
    private static final long serialVersionUID = 1L;

    public StuNoException()
    {
        super("学号", null);
    }
}

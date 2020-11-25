package com.stdiet.common.exception.user;

import com.stdiet.common.exception.BaseException;

/**
 * 用户信息异常类
 * 
 * @author stdiet
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}

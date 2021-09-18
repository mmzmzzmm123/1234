package com.xiaobear.common.exception.user;

import com.xiaobear.common.exception.BaseException;

/**
 * 用户信息异常类
 * 
 * @author xiaobear
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}

package com.hxzh.front.common.exception.task;


import com.hxzh.front.common.exception.BaseException;

public class TaskException extends BaseException {
    private static final long serialVersionUID = 1L;

    public TaskException(String code, Object[] args) {
        super("task", code, args);
    }
    public TaskException(String code, Object[] args, String defaultMessage) {
        super("task", code, args, defaultMessage);
    }

}

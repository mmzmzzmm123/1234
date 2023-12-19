package com.hxzh.front.common.exception.task;

import com.hxzh.front.common.constant.TaskConstants;

public class TaskAuthCodeNotMatchException extends TaskException{
    private static final long serialVersionUID = 1L;

    public TaskAuthCodeNotMatchException() {
        super("task.authcode.not.match", null, TaskConstants.TASKNOTMATCH);
    }
}

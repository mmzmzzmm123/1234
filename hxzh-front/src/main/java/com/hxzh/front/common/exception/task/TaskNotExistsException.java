package com.hxzh.front.common.exception.task;

import com.hxzh.front.common.constant.TaskConstants;

public class TaskNotExistsException extends TaskException {
    private static final long serialVersionUID = 1L;

    public TaskNotExistsException() {
        super("task.not.exists", null, TaskConstants.NOTASK);
    }
}

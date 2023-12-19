package com.hxzh.front.common.exception.task;

public class TaskAuthCodeRetryLimitExceedException extends TaskException {
    private static final long serialVersionUID = 1L;

    public TaskAuthCodeRetryLimitExceedException(int retryLimitCount, int lockTime) {
        super("task.authcode.retry.limit.exceed", new Object[] { retryLimitCount, lockTime });
    }
}

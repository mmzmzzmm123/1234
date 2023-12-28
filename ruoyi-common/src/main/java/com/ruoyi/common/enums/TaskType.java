package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author Jing.Zhang
 */
@Getter
public enum TaskType {

    JOIN_CHAT_ROOM("拉群", 0);

    /**
     * 任务名称
     */
    private final String taskName;

    /**
     * 任务类型
     */
    private final Integer taskType;

    TaskType(String taskName, Integer taskType) {
        this.taskName = taskName;
        this.taskType = taskType;
    }
}

package com.hxzh.front.common.enums;

public enum TaskStatus {
    CANCEL(-1, "已取消"),
    UNCONFIRMED(0, "未确认"),
    OK(1, "待检测"),
    DETECTING(2, "检测中"),
    DETECTED(3, "已检测"),
    COMPLETED(4, "已下发");


    private final int value;
    private final String name;

    TaskStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getNameByValue(int value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getValue() == value) {
                return status.getName();
            }
        }
        return null;
    }
}
package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

/**
 * @author 朱少波
 * @date 2024/1/17/017 9:30
 */
@AllArgsConstructor
@Getter
public enum SetAdminAction {


    QUERY_GROUP_MEMBER(0,GroupAction.QUERY_GROUP_MEMBER,0),

    QUERY_HASH(1, GroupAction.QUERY_HASH, 5),

    SET_BOT_ADMIN(2, GroupAction.SET_GROUP_ADMIN, 0);

    private final int code;

    private final GroupAction action;

    private final int retryCount;


    public static SetAdminAction of(Integer code) {
        if (code == null) {
            return null;
        }
        return EnumSet.allOf(SetAdminAction.class)
                .stream()
                .filter(p -> p.getCode() == code)
                .findAny()
                .orElse(null);
    }

    public SetAdminAction getNextAction() {
        return EnumSet.allOf(SetAdminAction.class)
                .stream()
                .filter(p -> p.getCode() == code + 1)
                .findAny()
                .orElse(null);
    }
}

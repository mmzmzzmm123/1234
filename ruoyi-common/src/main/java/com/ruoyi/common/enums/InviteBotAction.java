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
public enum InviteBotAction {

    SEARCH_BOT(0, GroupAction.SEARCH_BOT, 0),

    ADD_BOT(1, GroupAction.ADD_BOT, 0),

    INVITE_BOT_JOIN_GROUP(2, GroupAction.INVITE_BOT_JOIN_GROUP, 0),

    SET_BOT_ADMIN(3, GroupAction.SET_GROUP_ADMIN, 0);

    private final int code;

    private final GroupAction action;

    private final int retryCount;


    public static InviteBotAction getAction(Integer code) {
        if (code == null) {
            return null;
        }
        return EnumSet.allOf(InviteBotAction.class)
                .stream()
                .filter(p -> p.getCode() == code)
                .findAny()
                .orElse(null);
    }

    public InviteBotAction getNextAction(Integer retryCount) {
        if (this.retryCount > retryCount) {
            return this;
        }
        return EnumSet.allOf(InviteBotAction.class)
                .stream()
                .filter(p -> p.getCode() == code + 1)
                .findAny()
                .orElse(null);
    }
}

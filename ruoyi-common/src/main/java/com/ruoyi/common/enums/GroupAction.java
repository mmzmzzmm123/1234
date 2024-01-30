package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

/**
 * @author 朱少波  0-设置头像 1-设置群名 2-设置群类型 3-搜索bot 4-添加bot 5-邀请bot进入群聊 6-设置管理员
 * @date 2024/1/17/017 9:30
 */
@AllArgsConstructor
@Getter
public enum GroupAction {

    SET_GROUP_IMAGE(0, "设置群头像", true, LimitingDimensions.NONE, null, null, false, false),

    SET_GROUP_NAME(1, "设置群名", true, LimitingDimensions.NONE, null, null, false, false),

    SET_GROUP_TYPE(2, "设置群类型", true, LimitingDimensions.NONE, null, null, false, false),

    SEARCH_BOT(3, "搜索BOT", true, LimitingDimensions.NONE, null, null, false, false),

    ADD_BOT(4, "添加BOT", true, LimitingDimensions.NONE, null, null, false, false),

    INVITE_BOT_JOIN_GROUP(5, "邀请bot进入群聊", true, LimitingDimensions.ROBOT, 5 * 60L, 10, false, false),

    SET_GROUP_ADMIN(6, "设置管理员", true, LimitingDimensions.NONE, null, null, false, true),

    QUERY_HASH(7, "查询成员hash", true, LimitingDimensions.ALL, 60L, 50, true, false),

    QUERY_GROUP_MEMBER(8, "查询群成员", true, LimitingDimensions.NONE, null, null, false, false);

    private final int code;

    private final String name;

    private final boolean callBack;

    private final LimitingDimensions limitingDimensions;

    private final Long limitTime;

    private final Integer limitFrequency;

    private final Boolean needCacheOpt;

    private final Boolean limitOne;


    public static GroupAction of(Integer code) {
        if (code == null) {
            return null;
        }
        return EnumSet.allOf(GroupAction.class)
                .stream()
                .filter(p -> p.getCode() == code)
                .findAny()
                .orElse(null);
    }
}

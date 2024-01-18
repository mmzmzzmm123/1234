package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 朱少波  0-设置头像 1-设置群名 2-设置群类型 3-搜索bot 4-添加bot 5-邀请bot进入群聊 6-设置管理员
 * @date 2024/1/17/017 9:30
 */
@AllArgsConstructor
@Getter
public enum GroupAction {

    SET_GROUP_IMAGE(0, "设置群头像", true),

    SET_GROUP_NAME(1, "设置群名", true),

    SET_GROUP_TYPE(2, "设置群类型", true),

    SEARCH_BOT(3, "搜索BOT", true),

    ADD_BOT(4, "添加BOT", true),

    INVITE_BOT_JOIN_GROUP(5, "邀请bot进入群聊", true),

    SET_GROUP_ADMIN(6, "设置管理员", true);

    private final int code;

    private final String name;

    private final boolean callBack;

}

package com.ruoyi.common.enums;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:31
 */
public enum BotEventEnum {
    /**
     * 成员加入群聊
     */
    JOIN_GROUP,

    /**
     * 收到消息
     */
    RECEIVE_MESSAGE,

    /**
     * 成员离开群聊
     */
    LEAVE_GROUP,

    /**
     * BOT 加入群聊
     */
    BOT_JOIN_NEW_GROUP,

    /**
     * 群聊升级
     */
    GROUP_UPGRADE,

    /**
     * 机器人消息
     */
    BOT_COMMAND,

    /**
     * 群人数统计
     */
    GROUP_MEMBER_COUNT,

    /**
     * 未知事件
     */
    UNKNOWN;
}

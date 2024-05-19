package com.onethinker.common.enums;

/**
 * 文件关联类型
 * @author yangyouqi
 * @date 2024/5/15
 */
public enum FileRelationTypeEnum {
    /**
     * 头像
     */
    AVATAR("头像"),
    /**
     * 在线会话
     */
    CONVERSATION("在线会话"),
    /**
     * 群聊
     */
    GROUP("群聊"),
    /**
     * 群公告文件
     */
    GROUP_NOTICE("群公告文件"),
    /**
     * 私聊
     */
    PRIVATE("私聊"),
    /**
     * 常用资料
     */
    COMMON_DATA("常用资料"),
    /**
     * 知识库
     */
    KNOWLEDGE("知识库"),
    /**
     * 工单
     */
    ORDER("工单"),
    /**
     * 需求申请
     */
    REQ("需求申请"),
    /**
     * 需求申请审核
     */
    REQ_AUDIT("需求申请审核"),
    /**
     * 临时文件
     */
    TEMP("临时文件"),
    /**
     * 消息通知
     */
    MESSAGE_NOTICE("消息通知"),
    /**
     * 权限申请
     */
    PERMISSION_APPLY("权限申请"),

    /**
     * 财厅处室业务需求申请
     */
    DEMAND_APPLY("财厅处室业务需求申请"),

    /**
     * 财政数据应用需求申请
     */
    DATA_APPLICATION_DEMAND_APPLY("财政数据应用需求申请"),

    /**
     * 业务数据技术支持需求申请(刷数)
     */
    BRUSH_DATA_DEMAND_APPLY("业务数据技术支持需求申请"),

    /**
     * 删数需求申请
     */
    DELETE_DATA_DEMAND_APPLY("删数需求申请"),

    /**
     * 功能建设需求申请
     */
    FUN_BUILD_DEMAND_APPLY("功能建设需求申请");

    private final String desc;

    FileRelationTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

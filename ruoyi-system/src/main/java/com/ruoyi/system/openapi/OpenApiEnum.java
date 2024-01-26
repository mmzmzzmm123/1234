package com.ruoyi.system.openapi;

import lombok.Getter;


@Getter
public enum OpenApiEnum {

    THIRD_USER_LOGIN_CLIENT("第三方登录接口", "/thirdparty/user/login/client", null),

    THIRD_TG_PERSONAL_ON("TG-个人号订阅", "/thirdparty/tgRegister/personalOn", null),


    // 开平群接口
    THIRD_KP_TG_MODIFY_CHATROOM_HEAD_IMAGE("kpTg-修改群头像", "/thirdparty/tgGroup/modifyChatroomHeadImage", 1100910049),
    THIRD_KP_TG_MODIFY_CHATROOM_NAME("kpTg-修改群名称", "/thirdparty/tgGroup/modifyChatroomName", null),
    THIRD_KP_TG_SET_CHATROOM_ADMIN("kpTg-设置群管理员", "/thirdparty/tgGroup/setChatroomAdmin", 1100910053),
    THIRD_KP_TG_JOIN_CHATROOM_BY_URL("kpTg-通过群链接入群", "/thirdparty/tgGroup/joinChatroomByUrl", 1100910039),
    THIRD_KP_TG_EXIT_CHATROOML("kpTg-退出群", "/thirdparty/tgGroup/exitChatroom", 1100910024),
    THIRD_KP_TG_SEARCH_MEMBER("kpTg-获取群成员列表", "/thirdparty/tgGroup/searchMember", 1100910113),

    THIRD_KP_TG_GET_CHATROOM_INFO("kpTg-获取群信息", "/thirdparty/tgGroup/getChatroomInfo", 1100910018),

    THIRD_KP_TG_SET_CHATROOM_TYPE("kpTg-设置群类型", "/thirdparty/tgGroup/setChatroomType", 1100910056),

    THIRD_KP_TG_SEARCH_KEYWORD("kpTg-搜索群、用户、频道", "/thirdparty/tgGroup/searchKeyword", 1100910017),

    THIRD_KP_TG_INVITE_JOIN_CHATROOM("kpTg-邀请加入群组", "/thirdparty/tgGroup/inviteJoinChatroom", 1100910015),

    THIRD_KP_TG_SEND_GROUP_MESSAGE("kpTg-发送群聊消息", "/thirdparty/tgGroup/sendGroupMessage", 1100910011),

    THIRD_KP_TG_GET_GROUP_MEMBER("kpTg-获取群成员", "/thirdparty/tgGroup/getGroupMember", null),


    // 开平号接口
    THIRD_KP_TG_MODIFY_ROBOT_HEAD_IMG("kpTg-修改号头像", "/thirdparty/tgRobot/modifyRobotHeadImg", 1100910016),
    THIRD_KP_TG_MODIFY_NAME("kpTg-修改号姓氏和名字", "/thirdparty/tgRobot/modifyName", 1100910033),
    THIRD_KP_TG_MODIFY_USERNAME("kpTg-设置用户名接口", "/thirdparty/tgRobot/modifyUserName", 1100910009),
    THIRD_KP_TG_BATCH_ROBOT_SIMPINFO("kpTg-获取hw账号基础信息", "/thirdparty/tgModule/batchRobotSimpInfo", null),
    THIRD_KP_TG_JOIN_USER("kpTg-应用机器人加入通讯录", "/thirdparty/tgRobot/joinUser", 1100910026),

    THIRD_KP_TG_SET_PHONE_VISIBILITY("kpTg-设置隐私手机号码谁可见接口", "/thirdparty/tgRobot/setPhoneVisibility", 1100910083),


    // kp
    THIRD_KP_TG_SQL_TASK_SUBMIT("kpTg-SQL执行接口", "/thirdparty/tgModule/sqlTaskSubmit", null),
    THIRD_KP_TG_DISPERSE_IMAGE("kpTg-获取离散图片", "/thirdparty/tgCharacter/disperseImage", 1100850508),

    THIRD_KP_TG_GET_APPOINT_GRADE_TEXT_LIST("kpTg-获取离散文字", "/thirdparty/tgCharacter/getAppointGradeTextList", 1100850405),

    THIRD_KP_TG_INSERT_EVENT("kpTg-生成域名接口", "/thirdparty/tgModule/insertEvent", null),


    // utchat接口
    THIRD_UTCHAT_TG_SELECT_ROBOT_LIST_BY_RADIO("utchatTg-查询分组下的机器号", "/thirdparty/tgUtChat/selectRobotListByRadio", null),

    THIRD_UTCHAT_TG_SYNC_GROUP_MEMBER("utchatTg-同步群成员", "/thirdparty/tgUtChat/syncGroupMember", null),

    THIRD_UTCHAT_TG_SELECT_GROUP_LIST("utchatTg-查询商家(机器号)的群", "/thirdparty/tgUtChat/selectGroupList", null),

    THIRD_UTCHAT_TG_SELECT_GROUP_MEMBER_LIST("utchatTg-查询群成员", "/thirdparty/tgUtChat/selectGroupMemberList", null),

    THIRD_UTCHAT_TG_SELECT_CHATROOM_ADMIN_DISTR_ALL("utchatTg-查询全量黑群主信息", "/thirdparty/tgUtChat/selectChatroomAdminDistrAll", null),

    THIRD_UTCHAT_TG_SYNC_CHATROOM_ADMIN_DISTR_ALL("utchatTg-同步全量黑群主信息", "/thirdparty/tgUtChat/syncChatroomAdminDistrAll", null),

    THIRD_UTCHAT_TG_SELECT_ROBOT_INFO_LIST("utchatTg-查询号信息", "/thirdparty/tgUtChat/selectRobotInfoList", null),

    THIRD_UTCHAT_TG_SYNC_GROUP_INVITE_LINK("utchatTg-同步群邀请链接", "/thirdparty/tgUtChat/syncGroupInviteLink", null),



    ;

    /**
     * 请求地址
     */
    private final String requestUrl;

    /**
     * Api描述
     */
    private final String apiDesc;

    /**
     * 回调编码
     */
    private final Integer callbackCode;

    OpenApiEnum(String apiDesc, String requestUrl, Integer callbackCode) {
        this.requestUrl = requestUrl;
        this.apiDesc = apiDesc;
        this.callbackCode = callbackCode;
    }
}

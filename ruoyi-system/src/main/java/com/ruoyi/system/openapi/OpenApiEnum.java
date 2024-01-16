package com.ruoyi.system.openapi;

import lombok.Getter;


@Getter
public enum OpenApiEnum {

    THIRD_USER_LOGIN_CLIENT("第三方登录接口", "/thirdparty/user/login/client", null),

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


    // 开平号接口
    THIRD_KP_TG_MODIFY_ROBOT_HEAD_IMG("kpTg-修改号头像", "/thirdparty/tgRobot/modifyRobotHeadImg", 1100910016),
    THIRD_KP_TG_MODIFY_NAME("kpTg-修改号姓氏和名字", "/thirdparty/tgRobot/modifyName", 1100910033),
    THIRD_KP_TG_MODIFY_USERNAME("kpTg-设置用户名接口", "/thirdparty/tgRobot/modifyUserName", 1100910009),
    THIRD_KP_TG_BATCH_ROBOT_SIMPINFO("kpTg-获取hw账号基础信息", "/thirdparty/tgModule/batchRobotSimpInfo", null),
    THIRD_KP_TG_JOIN_USER("kpTg-应用机器人加入通讯录", "/thirdparty/tgRobot/joinUser", 1100910026),

    // utchat接口
    THIRD_UTCHAT_TG_SELECT_ROBOT_LIST_BY_RADIO("utchatTg-查询分组下的机器号", "/thirdparty/tgUtChat/selectRobotListByRadio", null),

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

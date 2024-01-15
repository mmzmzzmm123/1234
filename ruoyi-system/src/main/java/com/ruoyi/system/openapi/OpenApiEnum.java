package com.ruoyi.system.openapi;

import lombok.Getter;


@Getter
public enum OpenApiEnum {

    THIRD_USER_LOGIN_CLIENT("第三方登录接口", "/thirdparty/user/login/client", 0),

    // 开平群接口
    THIRD_KP_TG_MODIFY_CHATROOM_HEAD_IMAGE("kpTg-修改群头像", "/thirdparty/tgGroup/modifyChatroomHeadImage", 0),
    THIRD_KP_TG_MODIFY_CHATROOM_NAME("kpTg-修改群名称", "/thirdparty/tgGroup/modifyChatroomName", 0),
    THIRD_KP_TG_SET_CHATROOM_ADMIN("kpTg-设置群管理员", "/thirdparty/tgGroup/setChatroomAdmin", 0),
    THIRD_KP_TG_JOIN_CHATROOM_BY_URL("kpTg-通过群链接入群", "/thirdparty/tgGroup/joinChatroomByUrl", 0),
    THIRD_KP_TG_EXIT_CHATROOML("kpTg-退出群", "/thirdparty/tgGroup/exitChatroom", 0),
    THIRD_KP_TG_SEARCH_MEMBER("kpTg-获取群成员列表", "/thirdparty/tgGroup/searchMember", 0),


    // 开平号接口
    THIRD_KP_TG_MODIFY_ROBOT_HEAD_IMG("kpTg-修改号头像", "/thirdparty/tgRobot/modifyRobotHeadImg", 0),
    THIRD_KP_TG_MODIFY_NAME("kpTg-修改号姓氏和名字", "/thirdparty/tgRobot/modifyName", 0),
    THIRD_KP_TG_MODIFY_USERNAME("kpTg-设置用户名接口", "/thirdparty/tgRobot/modifyUserName", 0),
    THIRD_KP_TG_BATCH_ROBOT_SIMPINFO("kpTg-获取hw账号基础信息", "/thirdparty/tgModule/batchRobotSimpInfo", 0),

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

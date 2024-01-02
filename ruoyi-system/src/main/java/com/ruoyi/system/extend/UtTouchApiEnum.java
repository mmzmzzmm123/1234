package com.ruoyi.system.extend;

import lombok.Getter;

/**
 * @author Jing.Zhang
 */
@Getter
public enum UtTouchApiEnum {

    MERCHANT_LOGIN("商家登录", "/api/Merchant/UserLogin"),

    CREATE_PRIVATE_MESSAGE_TASK("创建批量私信任务", "/api/ComPrivateMessageTaskService/CreatePrivateMessageTask"),

    STOP_PRIVATE_MESSAGE_TASK("暂停批量私信任务", "/api/ComPrivateMessageTaskService/StopPrivateMessageTask"),

    CANCEL_PRIVATE_MESSAGE_TASK("取消批量私信任务", "/api/ComPrivateMessageTaskService/CancelPrivateMessageTask"),

    START_PRIVATE_MESSAGE_TASK("开启批量私信任务", "/api/ComPrivateMessageTaskService/StartPrivateMessageTask"),

    /**
     * @deprecated deprecated By Jing.Zhang 接口数据不全已经被删除
     */
    @Deprecated
    GET_PRIVATE_MESSAGE_TASK_BY_PAGE("获取云号批量私信列表", "/api/ComPrivateMessageTaskService/GetPrivateMessageTaskByPage"),

    GET_PRIVATE_MESSAGE_TASK_PAGE("获取云号批量私信列表-数据全", "/api/ComPrivateMessageTaskService/GetPrivateMessageTaskPage"),

    DELETE_PRIVATE_MESSAGE_TASK("删除私聊任务(支持批量)", "/api/ComPrivateMessageTaskService/DelTasks"),

    ADD_TASK_OBJECT("追加任务目标", "/api/ComPrivateMessageTaskService/AddTaskObject"),

    GET_USER_MATERIALS_BY_PAGE("分页获取用户素材列表", "/api/Material/GetUserMaterialsByPage"),

    @Deprecated
    GET_USER_MATERIAL_CONTENTS_BY_PAGE("查询某个话术组的话术清单", "/api/Group/GetMaterialContentsByPage"),

    @Deprecated
    ADD_ONE_MATERIAL_CONTENT("增加某个话术组的内容", "/api/Group/AddOneMaterialContent"),

    @Deprecated
    DELETE_ONE_MATERIAL_CONTENT("删除某个话术组的内容", "/api/Group/RemoveOneMaterialContent"),

    GET_MATERIAL_GROUP("获取素材分组", "/api/Material/GetMaterialGroup"),

    SAVE_MATERIAL_GROUP("保存素材分组", "/api/Material/SaveMaterialGroup"),

    DELETE_MATERIAL_GROUP("删除素材分组", "/api/Material/DelMaterialGroup"),

    GET_MATERIAL_CONTENT_PAGE("获取素材内容分页数据", "/api/Material/GetMaterialContentPage"),

    SAVE_MATERIAL_CONTENT("保存素材内容", "/api/Material/SaveMaterialContent"),

    DELETE_MATERIAL_CONTENT("删除素材内容", "/api/Material/DelMaterialContent"),

    FORBIDDEN_MATERIAL_CONTENT("禁用素材内容", "/api/Material/ForbiddenMaterialContent"),

    GET_PRIVATE_MESSAGE_TASK_DETAIL("获取私信明细", "/api/ComPrivateMessageTaskService/GetPrivateMessageTaskDetail"),

    GET_POOL_COUNTRY_AREA_COUNTS("获取按照国家统计", "/api/PlatformRobot/GetPoolCountryAreaCounts"),

    ADD_TASK_MATERIAL_CONTENT("增加任务话术内容（默认加到第一个话术分组下）", "/api/ComPrivateMessageTaskService/AddTaskMaterialContent"),

    GET_PRIVATE_MESSAGE_TASK_MATERIALS("获取私信任务的话术统计", "/api/ComPrivateMessageTaskService/GetPrivateMessageTaskMaterials"),

    COUNTRY_BALANCE_SUMMARY("按照国家统计私信余量", "/api/PlatformRobot/CountryBalanceSummary"),
    GET_PLATFORM_ROBOT_POOL_PAGE("分页获取公域号池", "/api/PlatformRobot/GetPlatformRobotPoolPage"),


    SAVE_CHAT_ROOM_JOIN_TASK("保存拉群任务", "/api/ChatRoomJoinTask/SaveChatRoomJoinTask"),

    GET_CHAT_ROOM_JOIN_TASK_PAGE("获取拉群任务分页数据", "/api/ChatRoomJoinTask/GetChatRoomJoinTaskPage"),

    GET_CHAT_ROOM_JOIN_TASK_DETAIL("获取任务明细", "/api/ChatRoomJoinTask/GetChatRoomJoinTaskDetail"),

    CANCEL_CHAT_ROOM_JOIN("取消拉群", "/api/ChatRoomJoinTask/CancelChatRoomJoin"),

    STOP_CHAT_ROOM_JOIN("暂停开启拉群任务", "/api/ChatRoomJoinTask/StopChatRoomJoin"),

    DEL_CHAT_ROOM_JOIN_TASK("删除任务", "/api/ChatRoomJoinTask/DelChatRoomJoinTask"),

    CANCEL_CHAT_ROOM_JOIN_TASK("取消拉群任务", "/api/ChatRoomJoinTask/CancelChatRoomJoinTask"),

    STOP_CHAT_ROOM_JOIN_TASK("暂停拉群任务", "/api/ChatRoomJoinTask/StopChatRoomJoinTask"),

    START_CHAT_ROOM_JOIN_TASK("重新开启拉群任务", "/api/ChatRoomJoinTask/StartChatRoomJoinTask"),

    GET_ALL_COUNTRYS("获取所有国家", "/api/SysCommon/GetAllCountrys"),

    ADD_TASK_OBJECT_CHAT_ROOM_JOIN("追加任务目标", "/api/ChatRoomJoinTask/AddTaskObject"),

    UPDATE_CHAT_ROOM_TASK_INVITE_RULE("修改拉人规则", "/api/ChatRoomJoinTask/UpdateChatRoomTaskInviteRule"),

    GET_CHAT_ROOM_TASK_INVITE_RULE("获取拉人规则", "/api/ChatRoomJoinTask/GetChatRoomTaskInviteRule"),

    UPDATE_CHAT_ROOM_TASK("任务加速与减速", "/api/ChatRoomJoinTask/UpdateChatRoomTask"),

    SET_CHATROOM_NAME("修改群名称", "/api/ChatRoomJoinTask/SetChatroomName"),

    SET_CHATROOM_HEAD_IMG("修改群头像", "/api/ChatRoomJoinTask/SetChatroomHeadImg"),

    GET_CHATROOM_MEMBERS("获取群成员", "/api/ChatRoomJoinTask/GetChatroomMembers"),


    SYNC_CHATROOM_MEMBER("同步群成员", "/api/ChatRoomJoinTask/SyncChatroomMember"),

    SET_CHATROOM_ADMIN("设置群管理员", "/api/ChatRoomJoinTask/SetChatroomAdmin"),

    TRANSFER_CHATROOM_OWNER("转让群主", "/api/ChatRoomJoinTask/TransferChatroomOwner"),

    GET_CHAT_ROOM_JOIN_TASK_DETAIL_STATISTICS("获取群统计", "/api/ChatRoomJoinTask/GetChatRoomJoinTaskDetailStatistics"),

    GET_CHAT_ROOM_JOIN_TASK_DETAIL_INFO_LIST("获取拉人明细", "/api/ChatRoomJoinTask/GetChatRoomJoinTaskDetailInfoList"),

    GET_CHAT_ROOM_JOIN_TASK_ROBOT_LIST("获取账号明细", "/api/ChatRoomJoinTask/GetChatRoomJoinTaskRobotList"),

    COUNTRY_BUSINESS_ESTIMATE("获取机器人业务大盘", "/api/PlatformRobot/CountryBusinessEstimate"),


    ROBOT_JOIN_CHAT_ROOM("新增拉手号入群", "/api/ChatRoomJoinTask/RobotJoinChatRoom"),

    CHAT_ROOM_CHOOSE_ROBOT_EXIT("选择退出部分拉手号", "/api/ChatRoomJoinTask/ChatRoomChooseRobotExit"),

    GET_CHAT_ROOM_JOIN_TASK_DETAIL_STATISTICS_REPORT("获取拉群任务明细数据任务结果", "/api/ChatRoomJoinTask/GetChatRoomJoinTaskDetailStatisticsReport"),

    GET_CHAT_ROOM_NOT_OPEN_REPORT("拉群未开通列表报表", "/api/ChatRoomJoinTask/GetChatRoomNotOpenReport"),

    GET_CHAT_ROOM_REPORT("结果群列表报表", "/api/ChatRoomJoinTask/GetChatRoomReport"),

    GET_CHAT_ROOM_SUCCESS_REPORT("拉群成功列表报表", "/api/ChatRoomJoinTask/GetChatRoomSuccessReport"),

    GET_CHAT_ROOM_FAIL_REPORT("拉群失败列表报表", "/api/ChatRoomJoinTask/GetChatRoomFailReport"),

    GET_CHAT_ROOM_SET_PRIVITE_REPORT("拉群目标隐私列表报表", "/api/ChatRoomJoinTask/GetChatRoomSetPriviteReport"),

    GET_CHAT_ROOM_NOT_USE_REPORT("拉群未使用列表报表", "/api/ChatRoomJoinTask/GetChatRoomNotUseReport"),

    GET_CHAT_ROOM_NOT_RUN_REPORT("拉群未执行列表报表", "/api/ChatRoomJoinTask/GetChatRoomNotRunReport"),

    GET_CHAT_ROOM_RUNNING_REPORT("拉群进行中列表报表", "/api/ChatRoomJoinTask/GetChatRoomRunningReport"),

    EXPORT_BATCH_PRIVATE_MESSAGE("拉群任务结束统计结果导出", "/api/ChatRoomJoinTask/ExportBatchPrivateMessage")

    ;

    /**
     * 请求地址
     */
    private final String requestUrl;

    /**
     * Api描述
     */
    private final String apiDesc;

    UtTouchApiEnum(String apiDesc, String requestUrl) {
        this.requestUrl = requestUrl;
        this.apiDesc = apiDesc;
    }
}

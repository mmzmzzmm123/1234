package com.ruoyi.system.extend;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.system.extend.data.*;

/**
 * @author Jing.Zhang
 */
public abstract class UtTouchJoinRoomClient {

    /**
     * 账号登录,获取token
     *
     * @return
     */
    public static UtTouchResult<UserLoginOutput> userLogin(UserLoginInputDTO input) {
        return UtTouchClient.post(UtTouchApiEnum.USER_LOGIN, JSONObject.from(input), UserLoginOutput.class);
    }

    /**
     * 保存拉群任务
     *
     * @return
     */
    public static UtTouchResult<String> saveChatRoomJoinTask(SaveChatRoomJoinTaskDTO input) {
        return UtTouchClient.post(UtTouchApiEnum.SAVE_CHAT_ROOM_JOIN_TASK, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<UtTouchPage<GetChatRoomJoinTaskPageOutput>> getChatRoomJoinTaskPage(GetChatRoomJoinTaskPageInput input) {
        return UtTouchClient.post(UtTouchApiEnum.GET_CHAT_ROOM_JOIN_TASK_PAGE,
                JSONObject.from(input),
                new TypeReference<UtTouchPage<GetChatRoomJoinTaskPageOutput>>(GetChatRoomJoinTaskPageOutput.class) {
                });
    }


    public static UtTouchResult<GetChatRoomJoinTaskDetailOutput> getChatRoomJoinTaskDetail(GetChatRoomJoinTaskDetailInput input) {
        return UtTouchClient.post(UtTouchApiEnum.GET_CHAT_ROOM_JOIN_TASK_DETAIL,
                JSONObject.from(input),
                GetChatRoomJoinTaskDetailOutput.class);
    }

    public static UtTouchResult<String> cancelChatRoomJoin(CancelChatRoomJoinInput input) {
        return UtTouchClient.post(UtTouchApiEnum.CANCEL_CHAT_ROOM_JOIN, JSONObject.from(input), String.class);

    }

    public static UtTouchResult<String> stopChatRoomJoin(StopChatRoomJoinInput input) {
        return UtTouchClient.post(UtTouchApiEnum.STOP_CHAT_ROOM_JOIN, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<String> delChatRoomJoinTask(DeleteChatRoomJoinTaskInput input) {
        return UtTouchClient.post(UtTouchApiEnum.DEL_CHAT_ROOM_JOIN_TASK, JSONObject.from(input), String.class);
    }


    public static UtTouchResult<String> cancelChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        return UtTouchClient.post(UtTouchApiEnum.CANCEL_CHAT_ROOM_JOIN_TASK, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<String> stopChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        return UtTouchClient.post(UtTouchApiEnum.STOP_CHAT_ROOM_JOIN_TASK, JSONObject.from(input), String.class);
    }


    public static UtTouchResult<String> startChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        return UtTouchClient.post(UtTouchApiEnum.START_CHAT_ROOM_JOIN_TASK, JSONObject.from(input), String.class);
    }


    public static UtTouchResult<String> addTaskObject(AddTaskObjectData input) {
        return UtTouchClient.post(UtTouchApiEnum.ADD_TASK_OBJECT_CHAT_ROOM_JOIN, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<UpdateTaskRuleData> getChatRoomTaskInviteRule(GetChatRoomTaskInviteRuleData input) {
        return UtTouchClient.post(UtTouchApiEnum.GET_CHAT_ROOM_TASK_INVITE_RULE, JSONObject.from(input), UpdateTaskRuleData.class);
    }


    public static UtTouchResult<String> updateChatRoomTaskInviteRule(UpdateTaskRuleData input) {
        return UtTouchClient.post(UtTouchApiEnum.UPDATE_CHAT_ROOM_TASK_INVITE_RULE, JSONObject.from(input), String.class);
    }


    public static UtTouchResult<String> updateChatRoomTask(UpdateChatRoomTaskInput input) {
        return UtTouchClient.post(UtTouchApiEnum.UPDATE_CHAT_ROOM_TASK, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<String> setChatroomName(SetChatroomNameInput input) {
        return UtTouchClient.post(UtTouchApiEnum.SET_CHATROOM_NAME, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<String> setChatroomHeadImg(SetChatroomHeadImgInput input) {
        return UtTouchClient.post(UtTouchApiEnum.SET_CHATROOM_HEAD_IMG, JSONObject.from(input), String.class);
    }


    public static UtTouchResult<UtTouchPage<GetChatroomMembersOutput>> getChatroomMembers(GetChatroomMembersInput input) {
        return UtTouchClient.post(UtTouchApiEnum.GET_CHATROOM_MEMBERS,
                JSONObject.from(input),
                new TypeReference<UtTouchPage<GetChatroomMembersOutput>>(GetChatroomMembersOutput.class) {
                });
    }

    public static UtTouchResult<String> syncChatroomMember(SyncChatroomMembersInput input) {
        return UtTouchClient.post(UtTouchApiEnum.SYNC_CHATROOM_MEMBER, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<String> setChatroomAdmin(SetChatroomAdminInput input) {
        return UtTouchClient.post(UtTouchApiEnum.SET_CHATROOM_ADMIN, JSONObject.from(input), String.class);
    }


    public static UtTouchResult<String> transferChatroomOwner(SetChatroomAdminInput input) {
        return UtTouchClient.post(UtTouchApiEnum.TRANSFER_CHATROOM_OWNER, JSONObject.from(input), String.class);
    }

    public static UtTouchResult<UtTouchPage<GetChatRoomJoinTaskRobotListOutput>>
    getChatRoomJoinTaskRobotList(GetChatRoomJoinTaskRobotListInput input) {
        return UtTouchClient.post(UtTouchApiEnum.GET_CHAT_ROOM_JOIN_TASK_ROBOT_LIST,
                JSONObject.from(input),
                new TypeReference<UtTouchPage<GetChatRoomJoinTaskRobotListOutput>>(GetChatRoomJoinTaskRobotListOutput.class) {
                });
    }

    public static UtTouchResult<UtTouchPage<GetChatRoomJoinTaskDetailInfoListOutput>>
    getChatRoomJoinTaskDetailInfoList(GetChatRoomJoinTaskDetailInfoListInput input) {
        return UtTouchClient.post(UtTouchApiEnum.GET_CHAT_ROOM_JOIN_TASK_DETAIL_INFO_LIST,
                JSONObject.from(input),
                new TypeReference<UtTouchPage<GetChatRoomJoinTaskDetailInfoListOutput>>(GetChatRoomJoinTaskDetailInfoListOutput.class) {
                });
    }

    public static UtTouchResult<GetChatRoomJoinTaskDetailStatisticsOutput>
    getChatRoomJoinTaskDetailStatistics(GetChatRoomJoinTaskDetailStatisticsInput input) {
        return UtTouchClient.post(UtTouchApiEnum.GET_CHAT_ROOM_JOIN_TASK_DETAIL_STATISTICS,
                JSONObject.from(input),
                GetChatRoomJoinTaskDetailStatisticsOutput.class);
    }

    public static UtTouchResult<CountryBusinessEstimateOutput> countryBusinessEstimate(CountryBusinessEstimateInput input) {
        return UtTouchClient.post(UtTouchApiEnum.COUNTRY_BUSINESS_ESTIMATE,
                JSONObject.from(input),
                CountryBusinessEstimateOutput.class);
    }

    public static UtTouchResult<String> chatRoomChooseRobotExit(ChatRoomChooseRobotExitInput input) {
        return UtTouchClient.post(UtTouchApiEnum.CHAT_ROOM_CHOOSE_ROBOT_EXIT,
                JSONObject.from(input),
                String.class);
    }

    public static UtTouchResult<String> robotJoinChatRoom(RobotJoinChatRoomInput input) {
        return UtTouchClient.post(UtTouchApiEnum.ROBOT_JOIN_CHAT_ROOM,
                JSONObject.from(input),
                String.class);
    }

    public static UtTouchResult<PriorityTarget> countryBusinessPriorityCountryRule(CountryBusinessPriorityRuleReq input) {
        return UtTouchClient.post(UtTouchApiEnum.COUNTRY_BUSINESS_PRIORITY_COUNTRY_RULE,
                JSONObject.from(input),
                PriorityTarget.class);
    }
}

package com.ruoyi.web.business;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.components.MultipartFileUtil;
import com.ruoyi.system.components.PageConvertUtil;
import com.ruoyi.system.domain.vo.AnalysisUploadPhoneResultVO;
import com.ruoyi.system.domain.vo.UbpmCountryVO;
import com.ruoyi.system.extend.*;
import com.ruoyi.system.extend.data.*;
import com.ruoyi.system.service.CountryService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jing.Zhang
 */
@Service
@AllArgsConstructor
public class UbpmPlusJoinChatRoomService {

    private final UtTouchProperties utTouchProperties;

    private final CountryService countryService;


    public void saveChatRoomJoinTask(SaveChatRoomJoinTaskDTO input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        input.setExtendKey(SecurityUtils.getUserId().toString());
        UtTouchResult<String> result = UtTouchJoinRoomClient.saveChatRoomJoinTask(input);
        result.failedAndThrow();

        // todo jing.zhang 这个数据要保存在自己库里面
    }

    public Page<GetChatRoomJoinTaskPageOutput> getChatRoomJoinTaskPage(GetChatRoomJoinTaskPageInput input) {
        // todo jing.zhang 需要先查自己库 做权限控制

        input.setUserCode(utTouchProperties.getTouchMerchantId());
        input.setExtendKey(SecurityUtils.getUserId().toString());
        UtTouchResult<UtTouchPage<GetChatRoomJoinTaskPageOutput>> touchResult =
                UtTouchJoinRoomClient.getChatRoomJoinTaskPage(input);
        return Optional.of(touchResult)
                .map(UtTouchResult::getData)
                .map(PageConvertUtil::convertUtTouchPage)
                .orElse(PageConvertUtil.defaultPage());
    }

    public GetChatRoomJoinTaskDetailOutput getChatRoomJoinTaskDetail(GetChatRoomJoinTaskDetailInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<GetChatRoomJoinTaskDetailOutput> result = UtTouchJoinRoomClient.getChatRoomJoinTaskDetail(input);
        return result.getDataOrThrow();
    }

    public void cancelChatRoomJoin(CancelChatRoomJoinInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.cancelChatRoomJoin(input);
        result.failedAndThrow();
    }


    public void stopChatRoomJoin(StopChatRoomJoinInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.stopChatRoomJoin(input);
        result.failedAndThrow();
    }

    public void delChatRoomJoinTask(DeleteChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.delChatRoomJoinTask(input);
        result.failedAndThrow();
    }


    public void cancelChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.cancelChatRoomJoinTask(input);
        result.failedAndThrow();
    }

    public void stopChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.stopChatRoomJoinTask(input);
        result.failedAndThrow();
    }

    public void startChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.startChatRoomJoinTask(input);
        result.failedAndThrow();
    }

    public AnalysisUploadPhoneResultVO analysisUploadFile(MultipartFile file) {
        List<String> uploadPhones = new ArrayList<>();
        if (MultipartFileUtil.isText(file)) {
            uploadPhones = MultipartFileUtil.analyseTextFile(file);
        } else {
            throw new GlobalException("不支持的格式");
        }

        /**
         * 1. 去重
         * 2. 获取手机号对应的国家
         * 3. 按照手机号对应国家进行分组计数
         */
        Map<String, Long> countByCountry = uploadPhones.stream()
                .distinct()
                .map(countryService::getCountriesByPhone)
                .peek(it -> {
                    if (StringUtils.isBlank(it.getCountryName())) {
                        it.setCountryName("未知");
                    }
                })
                .collect(Collectors.groupingBy(UbpmCountryVO::getCountryName, Collectors.counting()));

        return new AnalysisUploadPhoneResultVO(countByCountry, uploadPhones);


    }

    public void addTaskObject(AddTaskObjectData input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.addTaskObject(input);
        result.failedAndThrow();
    }

    public void updateChatRoomTaskInviteRule(UpdateTaskRuleData input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.updateChatRoomTaskInviteRule(input);
        result.failedAndThrow();
    }

    public void updateChatRoomTask(UpdateChatRoomTaskInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.updateChatRoomTask(input);
        result.failedAndThrow();
    }

    public UpdateTaskRuleData getChatRoomTaskInviteRuleData(
            GetChatRoomTaskInviteRuleData input) {
        UtTouchResult<UpdateTaskRuleData> result = UtTouchJoinRoomClient.getChatRoomTaskInviteRule(input);
        return result.getDataOrThrow();
    }

    public void setChatroomName(SetChatroomNameInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.setChatroomName(input);
        result.failedAndThrow();
    }


    public void setChatroomAdmin(SetChatroomAdminInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.setChatroomAdmin(input);
        result.failedAndThrow();
    }

    public void setChatroomHeadImg(SetChatroomHeadImgInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.setChatroomHeadImg(input);
        result.failedAndThrow();
    }

    public void syncChatroomMember(SyncChatroomMembersInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.syncChatroomMember(input);
        result.failedAndThrow();
    }


    public void transferChatroomOwner(SetChatroomAdminInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.transferChatroomOwner(input);
        result.failedAndThrow();
    }

    public Page<GetChatroomMembersOutput> getChatroomMembers(GetChatroomMembersInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<UtTouchPage<GetChatroomMembersOutput>> result = UtTouchJoinRoomClient.getChatroomMembers(input);
        return PageConvertUtil.convertUtTouchPage(result.getData());
    }

    public Page<GetChatRoomJoinTaskRobotListOutput> getChatRoomJoinTaskRobotList(GetChatRoomJoinTaskRobotListInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<UtTouchPage<GetChatRoomJoinTaskRobotListOutput>> result = UtTouchJoinRoomClient.getChatRoomJoinTaskRobotList(input);
        return PageConvertUtil.convertUtTouchPage(result.getData());
    }

    public Page<GetChatRoomJoinTaskDetailInfoListOutput> getChatRoomJoinTaskDetailInfoList(GetChatRoomJoinTaskDetailInfoListInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<UtTouchPage<GetChatRoomJoinTaskDetailInfoListOutput>> result =
                UtTouchJoinRoomClient.getChatRoomJoinTaskDetailInfoList(input);
        return PageConvertUtil.convertUtTouchPage(result.getData());
    }

    public GetChatRoomJoinTaskDetailStatisticsOutput getChatRoomJoinTaskDetailStatistics(GetChatRoomJoinTaskDetailStatisticsInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<GetChatRoomJoinTaskDetailStatisticsOutput> result =
                UtTouchJoinRoomClient.getChatRoomJoinTaskDetailStatistics(input);
        return result.getDataOrThrow();
    }

    public CountryBusinessEstimateOutput countryBusinessEstimate(CountryBusinessEstimateInput input) {
        UtTouchResult<CountryBusinessEstimateOutput> result = UtTouchJoinRoomClient.countryBusinessEstimate(input);
        return result.getDataOrThrow();
    }
    public void chatRoomChooseRobotExit(ChatRoomChooseRobotExitInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.chatRoomChooseRobotExit(input);
        result.failedAndThrow();
    }

    public void robotJoinChatRoom(RobotJoinChatRoomInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.robotJoinChatRoom(input);
        result.failedAndThrow();
    }
}

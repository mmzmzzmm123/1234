package com.ruoyi.system.callback.processor;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.enums.BotDealFunctionsEnum;
import com.ruoyi.common.enums.BotEventEnum;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.MD5Utils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.callback.BotEvent;
import com.ruoyi.system.callback.dto.bot.*;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.domain.vo.GroupPlayInfoVO;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:50
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class BotEventProcessor {

    private final GroupMonitorInfoService groupMonitorInfoService;

    private final GroupInfoService groupInfoService;

    private final GroupStateService groupStateService;

    private final PlayMessagePushService playMessagePushService;

    @BotEvent(value = BotEventEnum.JOIN_GROUP, parameterClass = JoinGroupDTO.class)
    public void joinGroup(List<JoinGroupDTO> list) {

    }

    @BotEvent(value = BotEventEnum.LEAVE_GROUP, parameterClass = LeaveGroupDTO.class)
    public void leaveGroup(LeaveGroupDTO dto) {

    }

    @BotEvent(value = BotEventEnum.GROUP_UPGRADE, parameterClass = GroupUpgradeDTO.class)
    public void groupUpgrade(GroupUpgradeDTO dto) {
        GroupMonitorInfo groupMonitorInfo = groupMonitorInfoService.originalGroupIdChange(dto.getBotId() + "",
                dto.getOldGroupId() + "", dto.getNewGroupId() + "");
        if(groupMonitorInfo!=null){
            String newGroupId = getGroupSerialNo(dto.getNewGroupId());
            String oldGroupId = getGroupSerialNo(dto.getOldGroupId());
            groupInfoService.updateGroupSerialNo(groupMonitorInfo.getGroupId(),oldGroupId, newGroupId);
            groupStateService.recover(groupMonitorInfo.getGroupId());

            // 如果是待发送的群 需要把状态更新为暂停,并记录暂停原因
            playMessagePushService.pauseGroupPushWhenWaitSend(groupMonitorInfo.getGroupId(), "群升级-待升级完成");
        }else{
            //根据原始id md5获取加密后的编号
            String oldGroupId = getGroupSerialNo(dto.getOldGroupId());
            String newGroupId = getGroupSerialNo(dto.getNewGroupId());
            GroupInfo groupBySerialNo = groupInfoService.getGroupBySerialNo(oldGroupId, newGroupId);
            if (groupBySerialNo != null) {
                groupMonitorInfoService.updateOriginalGroupId(groupBySerialNo.getGroupId(), dto.getOldGroupId() +"", dto.getNewGroupId() + "");
                groupInfoService.updateGroupSerialNo(groupBySerialNo.getGroupId(),oldGroupId, newGroupId);
                groupStateService.recover(groupBySerialNo.getGroupId());
            }
        }
    }

    @BotEvent(value = BotEventEnum.BOT_JOIN_NEW_GROUP, parameterClass = BotJoinNewGroupDTO.class)
    public void botJoinNewGroup(BotJoinNewGroupDTO dto) {
        String groupSerialNo = getGroupSerialNo(dto.getChatId());
        GroupInfo groupBySerialNo = groupInfoService.getGroupBySerialNo(groupSerialNo);
        if (groupBySerialNo != null) {
            groupMonitorInfoService.updateOriginalGroupId(groupBySerialNo.getGroupId(), "",dto.getChatId() + "");
        } else {
            groupMonitorInfoService.updateOriginalGroupIdByBot(dto.getBotId() + "",
                    MD5Utils.getMD5(dto.getRobotUserId() + "", "UTF-8").toUpperCase(),
                    dto.getChatId() + "");
        }
    }

    @BotEvent(value = BotEventEnum.GROUP_MEMBER_COUNT, parameterClass = MemberQuantityDTO.class)
    public void groupMemberCount(MemberQuantityDTO dto) {
        int count = groupMonitorInfoService.updateInfo(dto);
        //根据原始id 更新失败
        if (count <= 0) {
            //根据原始id md5获取加密后的编号
            String groupSerialNo = getGroupSerialNo(dto.getChatId());
            GroupInfo groupBySerialNo = groupInfoService.getGroupBySerialNo(groupSerialNo);
            if (groupBySerialNo != null) {
                groupMonitorInfoService.updateInfo(groupBySerialNo.getGroupId(), dto);
            }
        }

    }

    @BotEvent(value = BotEventEnum.GROUP_BANED, parameterClass = GroupBanedDTO.class)
    public void groupBaned(GroupBanedDTO dto) {
        List<String> groupIds = groupMonitorInfoService.updateBanned(dto.getChatId() + "");
        //根据原始id 更新失败
        if (CollUtil.isNotEmpty(groupIds)) {
            groupStateService.banned(groupIds);
        }

    }

    @BotEvent(value = BotEventEnum.ADVERTISEMENT_FILTER, parameterClass = AdvertisementFilterDTO.class)
    public void advertisementFilter(AdvertisementFilterDTO dto) {
        GroupPlayInfoVO groupPlayInfoVO = groupMonitorInfoService.getGroupPlayInfoVO(String.valueOf(dto.getChatId()));
        if (ObjectUtils.isEmpty(groupPlayInfoVO)) {
            log.info("groupPlayInfoVO is null {}", dto.toString());
            return;
        }
        List<String> rule = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dto.getDealFunctions())) {
            for (String dealFunction : dto.getDealFunctions()) {
                BotDealFunctionsEnum functionsEnum = BotDealFunctionsEnum.findByKey(dealFunction);
                rule.add(functionsEnum.getValue());
            }
        }
        String groupId = groupPlayInfoVO.getGroupId();
        String fullName = dto.getFullName();
        String filterType = dto.getFilterType();

        String content = String.format("【广告监控-%s】群：%s，成员：%s，触发监控类型-%s;", String.join(",", rule), groupId, fullName, filterType);
        PlayExecutionLog log = new PlayExecutionLog();
        log.setPlayId(groupPlayInfoVO.getPlayId());
        log.setGroupId(groupPlayInfoVO.getGroupId());
        log.setContent(content);
        log.setType(PlayLogTyper.Advertising_Monitoring);
        SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
    }

    public String getGroupSerialNo(Long chatId) {
        String chatStr = Math.abs(chatId) + "";
        if (chatStr.startsWith("100")) {
            chatStr = chatStr.substring(3);
        }
        return MD5Utils.getMD5(chatStr, "UTF-8").toUpperCase();
    }
}

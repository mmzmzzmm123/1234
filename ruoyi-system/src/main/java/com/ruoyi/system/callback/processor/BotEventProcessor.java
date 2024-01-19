package com.ruoyi.system.callback.processor;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.enums.BotEventEnum;
import com.ruoyi.common.utils.MD5Utils;
import com.ruoyi.system.callback.BotEvent;
import com.ruoyi.system.callback.dto.bot.*;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.service.GroupInfoService;
import com.ruoyi.system.service.GroupMonitorInfoService;
import com.ruoyi.system.service.GroupStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
            groupInfoService.updateGroupSerialNo(groupMonitorInfo.getGroupId(), newGroupId);
        }else{
            //根据原始id md5获取加密后的编号
            String oldGroupId = getGroupSerialNo(dto.getOldGroupId());
            String newGroupId = getGroupSerialNo(dto.getNewGroupId());
            GroupInfo groupBySerialNo = groupInfoService.getGroupBySerialNo(oldGroupId, newGroupId);
            if (groupBySerialNo != null) {
                groupMonitorInfoService.updateOriginalGroupId(groupBySerialNo.getGroupId(), dto.getNewGroupId() + "");
                groupInfoService.updateGroupSerialNo(groupBySerialNo.getGroupId(), newGroupId);
            }
        }

    }

    @BotEvent(value = BotEventEnum.BOT_JOIN_NEW_GROUP, parameterClass = BotJoinNewGroupDTO.class)
    public void botJoinNewGroup(BotJoinNewGroupDTO dto) {
        String groupSerialNo = getGroupSerialNo(dto.getChatId());
        GroupInfo groupBySerialNo = groupInfoService.getGroupBySerialNo(groupSerialNo);
        if (groupBySerialNo != null) {
            groupMonitorInfoService.updateOriginalGroupId(groupBySerialNo.getGroupId(), dto.getChatId() + "");
        } else {
            groupMonitorInfoService.updateOriginalGroupId(dto.getBotId() + "",
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
                groupMonitorInfoService.updateOriginalGroupId(groupBySerialNo.getGroupId(), dto.getChatId() + "");
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
        } else {
            //根据原始id md5获取加密后的编号
            String groupSerialNo = getGroupSerialNo(dto.getChatId());
            GroupInfo groupBySerialNo = groupInfoService.getGroupBySerialNo(groupSerialNo);
            if (groupBySerialNo != null) {
                groupMonitorInfoService.updateOriginalGroupId(groupBySerialNo.getGroupId(), dto.getChatId() + "");
                groupMonitorInfoService.updateBannedById(groupBySerialNo.getGroupId());
                groupStateService.banned(Collections.singletonList(groupBySerialNo.getGroupId()));
            }
        }

    }


    public String getGroupSerialNo(Long chatId) {
        String chatStr = Math.abs(chatId) + "";
        if (chatStr.startsWith("100")) {
            chatStr = chatStr.substring(3);
        }
        return MD5Utils.getMD5(chatStr, "UTF-8").toUpperCase();
    }
}

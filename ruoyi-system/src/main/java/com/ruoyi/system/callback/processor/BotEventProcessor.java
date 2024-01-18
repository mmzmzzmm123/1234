package com.ruoyi.system.callback.processor;

import com.ruoyi.common.enums.BotEventEnum;
import com.ruoyi.system.callback.BotEvent;
import com.ruoyi.system.callback.dto.bot.BotJoinNewGroupDTO;
import com.ruoyi.system.callback.dto.bot.JoinGroupDTO;
import com.ruoyi.system.callback.dto.bot.LeaveGroupDTO;
import com.ruoyi.system.callback.dto.bot.MemberQuantityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:50
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class BotEventProcessor {

    @BotEvent(value = BotEventEnum.JOIN_GROUP, parameterClass = JoinGroupDTO.class)
    public void joinGroup(List<JoinGroupDTO> list) {

    }

    @BotEvent(value = BotEventEnum.LEAVE_GROUP, parameterClass = LeaveGroupDTO.class)
    public void leaveGroup(LeaveGroupDTO dto) {

    }

    @BotEvent(value = BotEventEnum.BOT_JOIN_NEW_GROUP, parameterClass = BotJoinNewGroupDTO.class)
    public void botJoinNewGroup(BotJoinNewGroupDTO dto) {

    }

    @BotEvent(value = BotEventEnum.GROUP_MEMBER_COUNT, parameterClass = MemberQuantityDTO.class)
    public void groupMemberCount(MemberQuantityDTO dto) {

    }
}

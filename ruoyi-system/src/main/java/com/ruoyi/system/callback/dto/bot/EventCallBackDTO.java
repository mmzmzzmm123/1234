package com.ruoyi.system.callback.dto.bot;

import com.ruoyi.common.enums.BotEventEnum;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:47
 */
@Data
public class EventCallBackDTO {
    private Object data;

    private BotEventEnum eventType;
}

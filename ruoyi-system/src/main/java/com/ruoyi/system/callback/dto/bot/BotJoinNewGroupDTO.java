package com.ruoyi.system.callback.dto.bot;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:52
 */
@NoArgsConstructor
@Data
public class BotJoinNewGroupDTO {

    private Long botId;
    private Long chatId;

}

package com.ruoyi.system.callback.dto.bot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:52
 */
@NoArgsConstructor
@Data
public class BotJoinNewGroupDTO {

    @ApiModelProperty("bot id")
    private Long botId;

    @ApiModelProperty("chat id")
    private Long chatId;

    @ApiModelProperty("robot user id")
    private Long robotUserId;

}

package com.ruoyi.system.bot.mode.output;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朱少波
 * @date 2024/1/24/024 14:41
 */
@Data
public class GroupStateVO {

    private String bio;
    private String chatId;
    private String inviteLink;
    private Boolean joinToSendMessages;
    private String title;
    private String type;
}

package com.ruoyi.system.callback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:52
 */
@Data
public class JoinGroupDTO {

    private String botUserId;
    private String chatId;
    private String createTime;
    private String fullName;
    private String id;
    private String inviteFullName;
    private String inviteUserId;
    private String inviteUsername;
    private Integer isDeletedMessage;
    private String joinTime;
    private String messageId;
    private String userId;
    private String username;
}

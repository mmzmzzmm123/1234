package com.ruoyi.system.callback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:52
 */
@NoArgsConstructor
@Data
public class LeaveGroupDTO {


    private String chatId;
    private String createTime;
    private String fullName;
    private String id;
    private Integer isDeletedMessage;
    private String leaveTime;
    private String messageId;
    private String operateFullName;
    private String operateUserId;
    private String operateUsername;
    private String userId;
    private String username;
}

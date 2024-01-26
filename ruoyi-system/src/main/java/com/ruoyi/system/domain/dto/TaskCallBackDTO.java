package com.ruoyi.system.domain.dto;

import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/26/026 13:47
 */

@Data
public class TaskCallBackDTO {

    private Integer status;
    private String result;
    private String taskNo;
    private String phone;
    private String userName;
    private String lastOnlineTime;
    private String callbackId;
}

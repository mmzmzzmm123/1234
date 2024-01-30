package com.ruoyi.system.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朱少波
 * @date 2024/1/26/026 13:47
 */

@NoArgsConstructor
@Data
public class TaskCallBackDTO {

    @JSONField(name = "Status")
    private Integer status;
    @JSONField(name = "Result")
    private String result;
    @JSONField(name = "TaskNo")
    private String taskNo;
    @JSONField(name = "Phone")
    private String phone;
    @JSONField(name = "UserName")
    private String userName;
    @JSONField(name = "LastOnlineTime")
    private String lastOnlineTime;
    @JSONField(name = "CallbackId")
    private String callbackId;
}

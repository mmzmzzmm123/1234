package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTgSyncGroupInviteLinkDTO {

    private List<ThirdTgUtChatInfo> data;

    @Data
    public static class ThirdTgUtChatInfo {
        @ApiModelProperty(
                value = "群编号",
                required = true
        )
        private String chatroomSerialNo;
        @ApiModelProperty(
                value = "机器人编号",
                required = true
        )
        private String robotSerialNo;
    }
}

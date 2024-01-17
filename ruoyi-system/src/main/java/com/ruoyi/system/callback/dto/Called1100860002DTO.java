package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Called1100860002DTO {

    @JSONField(name = "robot_code")
    private String robotCode;

    @JSONField(name = "member_id")
    private String memberId;

    @JSONField(name = "update_time")
    private String updateTime;

    @JSONField(name = "member_code")
    private String memberCode;

    @JSONField(name = "robot_id")
    private String robotId;

    @JSONField(name = "chatroom_code")
    private String chatroomCode;

    @JSONField(name = "access_hash")
    private String accessHash;

    @JSONField(name = "was_online")
    private String wasOnline;

    @JSONField(name = "chatroom_id")
    private String chatroomId;

    @JSONField(name = "json_obj")
    private String jsonObj;


}

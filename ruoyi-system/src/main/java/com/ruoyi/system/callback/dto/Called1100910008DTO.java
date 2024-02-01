package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Called1100910008DTO {

    @JSONField(name = "friend_list")
    private List<Called1100910008FriendDTO> friend_list;

    @JSONField(name = "chatroom_list")
    private List<Called1100910008ChatroomInfoDTO> chatroom_list;

    @Data
    public static class Called1100910008FriendDTO {

        @JSONField(name = "friend_serial_no")
        private String friendSerialNo;

        @JSONField(name = "Is_bot")
        private Boolean isBot;

        @JSONField(name = "phone")
        private String phone;

        @JSONField(name = "last_name")
        private String lastName;

        @JSONField(name = "first_name")
        private String firstName;

        @JSONField(name = "username")
        private String username;

        @JSONField(name = "status")
        private Called1100910008StatusDTO status;
    }

    @Data
    public static class Called1100910008StatusDTO {
        @JSONField(name = "was_online")
        private String wasOnline;
    }

    @Data
    public static class Called1100910008ChatroomInfoDTO {
        /**
         * 群编号
         */
        @JSONField(name = "chatroom_serial_no")
        private String chatroomSerialNo;

        /**
         * 总人数
         */
        @JSONField(name = "member_count")
        private String memberCount;

        @JSONField(name = "date_time")
        private String dateTime;

        /**
         * 群名称
         */
        @JSONField(name = "title")
        private String title;

        @JSONField(name = "type")
        private String type;
    }
}

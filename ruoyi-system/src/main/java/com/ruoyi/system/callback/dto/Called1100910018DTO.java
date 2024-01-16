package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Called1100910018DTO {

    @JSONField(name = "chatroom_info")
    private Called1100910018ChatroomInfoDTO chatroomInfo;

    public static class Called1100910018ChatroomInfoDTO {

        /**
         * 群名称
         */
        @JSONField(name = "chatroom_name")
        private String chatroomName;

        @JSONField(name = "chatroom_name_base")
        private String chatroomNameBase;

        /**
         * 群编号
         */
        @JSONField(name = "chatroom_serial_no")
        private String chatroomSerialNo;

        /**
         * 备注
         */
        @JSONField(name = "remark")
        private String remark;

        /**
         * 总人数
         */
        @JSONField(name = "member_count")
        private String memberCount;

        /**
         * 被踢人数
         */
        @JSONField(name = "kicked_count")
        private String kickedCount;

        /**
         * 封号数
         */
        @JSONField(name = "banned_count")
        private String bannedCount;

        /**
         * 在线人数
         */
        @JSONField(name = "online_member_count")
        private String onlineMemberCount;

        /**
         * 群简介
         */
        @JSONField(name = "about")
        private String about;

        /**
         * 群链接
         */
        @JSONField(name = "link")
        private String link;

        @JSONField(name = "private_link")
        private String privateLink;

        /**
         * 是否频道
         */
        @JSONField(name = "is_channel")
        private Boolean isChannel;

        /**
         * 群类型：10私密群 20公开群
         */
        @JSONField(name = "type")
        private String type;
    }

}

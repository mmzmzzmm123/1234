package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Called1100910015DTO {

    @JSONField(name = "chatroom_info")
    private Called1100910015ChatroomInfoDTO chatroomInfo;

    @JSONField(name = "member_list")
    private List<Called1100910015MemberDTO> memberList;

    @Data
    public static class Called1100910015ChatroomInfoDTO {
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
         * 总人数
         */
        @JSONField(name = "member_count")
        private String memberCount;
    }

    @Data
    public static class Called1100910015MemberDTO {

        /**
         * 群成员编号
         */
        @JSONField(name = "member_serial_no")
        private String memberSerialNo;

        /**
         * 姓氏
         */
        @JSONField(name = "first_name")
        private String firstName;

        /**
         * 名字
         */
        @JSONField(name = "last_name")
        private String lastName;

        /**
         * 用户名
         */
        @JSONField(name = "user_name")
        private String userName;

        /**
         * 手机号
         */
        @JSONField(name = "phone")
        private String phone;

        /**
         * 是否管理员
         */
        @JSONField(name = "is_admin")
        private Boolean isAdmin;

        @JSONField(name = "invite_serial_no")
        private String inviteSerialNo;
    }
}

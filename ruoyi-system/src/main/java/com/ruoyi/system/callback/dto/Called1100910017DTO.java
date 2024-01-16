package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Called1100910017DTO {

    /**
     * 群聊列表
     */
    @JSONField(name = "chatroom_list")
    private List<Called1100910017ChatroomInfoDTO> chatroomList;

    /**
     * 频道列表
     */
    @JSONField(name = "channel_list")
    private List<Called1100910017ChannelDTO> channelList;

    /**
     * 用户列表
     */
    @JSONField(name = "user_list")
    private List<Called1100910017UserDTO> userList;

    @Data
    public static class Called1100910017ChatroomInfoDTO {
        /**
         * 群名称
         */
        @JSONField(name = "chatroom_name")
        private String chatroomName;

        /**
         * 用户名
         */
        @JSONField(name = "user_name")
        private String userName;

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
    }

    @Data
    public static class Called1100910017ChannelDTO {

        /**
         * 频道名称
         */
        @JSONField(name = "channel_name")
        private String channelName;

        /**
         * 用户名
         */
        @JSONField(name = "user_name")
        private String userName;

        /**
         * 频道编号
         */
        @JSONField(name = "channel_serial_no")
        private String channelSerialNo;

        /**
         * 频道人员
         */
        @JSONField(name = "member_count")
        private String memberCount;

        @JSONField(name = "date_time")
        private String dateTime;
    }
    @Data
    public static class Called1100910017UserDTO {

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

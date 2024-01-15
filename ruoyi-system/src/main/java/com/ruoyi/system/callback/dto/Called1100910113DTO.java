package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Called1100910113DTO {

    /**
     * 群编号
     */
    @JSONField(name = "chatroom_serial_no")
    private String chatroomSerialNo;

    /**
     * 群用户总数
     */
    @JSONField(name = "member_count")
    private String memberCount;


    /**
     * 群成员列表
     */
    @JSONField(name = "member_list")
    private List<Called1100910113MemberDTO> memberList;

    public static class Called1100910113MemberDTO {

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
         * 状态
         */
        @JSONField(name = "status")
        private Called1100910113StatusDTO status;

        @JSONField(name = "was_online_date")
        private String wasOnlineDate;

        /**
         * 成员身份 0普通 1群主 2管理员
         */
        @JSONField(name = "member_type")
        private String memberType;

        /**
         * 多久时间在线
         */
        @JSONField(name = "last_seen_ago")
        private String lastSeenAgo;
    }

    @Data
    public static class Called1100910113StatusDTO {

        @JSONField(name = "expires")
        private String expires;

        /**
         * 最后在线时间
         */
        @JSONField(name = "was_online")
        private String wasOnline;

        /**
         * 成员在线状态：10在线 20离线 30近期曾上线 40上周 50上个月
         */
        @JSONField(name = "member_status")
        private Integer memberStatus;
    }
}

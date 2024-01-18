package com.ruoyi.system.callback.dto;

import lombok.Data;

import java.util.List;

@Data
public class Called1100910006DTO {

    private String chatroom_serial_no;

    private List<Called1100910006MemberDTO> member_list;

    @Data
    public static class Called1100910006MemberDTO {

        private String last_seen_ago;

        private String member_serial_no;

        private String phone;

        private String user_name;

        private String was_online_date;

        private String last_name;

        private String first_name;

        private Called1100910006StatusDTO status;

    }

    @Data
    public static class Called1100910006StatusDTO {
        private String expires;

        private Integer member_status;

        private String was_online;
    }
}
